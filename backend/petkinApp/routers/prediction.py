import json
import os
from datetime import datetime

import pandas as pd
import gdown
from fastapi import FastAPI, File, UploadFile, HTTPException, APIRouter, Form, Depends
from sqlalchemy.orm import Session, session
from torch import nn
import torch
from torchvision import transforms, models
from PIL import Image
from sklearn.preprocessing import MinMaxScaler
from httpx import AsyncClient
import torch.nn.functional as F

from petkinApp.database import get_db
from petkinApp.models import AIResult
from petkinApp.routers.pets import get_pet
from petkinApp.schemas.pets import PetDetailResponse

# 라우터 설정
router = APIRouter(prefix="/ai")
model = None


# 사용자 정의 모델 클래스 정의 (기존)
# class MultimodalModel(nn.Module):
#     def __init__(self, image_model, output_dim):
#         super(MultimodalModel, self).__init__()
#
#         self.image_model = image_model
#
#         # 추가 특징을 처리하는 MLP
#         self.fc_additional = nn.Sequential(
#             nn.Linear(0, 64),  # 나중에 forward에서 동적으로 설정
#             nn.ReLU(),
#             nn.Linear(64, 64)
#         )
#
#         # 이미지 특징과 추가 feature 결합 후 예측하는 fully connected layer
#         self.fc_combined = nn.Sequential(
#             nn.Linear(1000 + 64, 128),
#             nn.ReLU(),
#             nn.Linear(128, output_dim)
#         )

class MultimodalModel(nn.Module):
    def __init__(self, image_model, additional_input_dim, output_dim):
        super(MultimodalModel, self).__init__()
        self.image_model = image_model
        self.fc_additional = nn.Sequential(
            nn.Linear(additional_input_dim, 64),
            nn.ReLU(),
            nn.Linear(64, 64)
        )
        self.fc_combined = nn.Sequential(
            nn.Linear(1000 + 64, 128),
            nn.ReLU(),
            nn.Linear(128, output_dim)
        )

    def forward(self, image, additional_features):
        # EfficientNet에서 이미지 특징 추출
        image_features = self.image_model(image)

        # 추가 특징의 입력 크기 동적으로 설정
        self.fc_additional[0] = nn.Linear(additional_features.shape[1], 64).to(additional_features.device)

        # 추가 특징 처리
        additional_features = self.fc_additional(additional_features)

        # 이미지 특징과 추가 특징 결합
        combined_features = torch.cat((image_features, additional_features), dim=1)

        # 결합된 특징을 통해 최종 예측
        output = self.fc_combined(combined_features)
        return output


def download_model_from_google_drive(file_id, output_path):
    """Download the model file from Google Drive"""
    url = f"https://drive.google.com/uc?id={file_id}"
    gdown.download(url, output_path, quiet=False)

# 모델 초기화 및 저장
def initialize_model_and_save(model_path="model.pt"):
    global model
    additional_input_dim = 3  # 입력 피처 크기
    model = MultimodalModel(image_model=models.efficientnet_b0(pretrained=True), additional_input_dim=additional_input_dim, output_dim=7)
    print("Model initialized.")

    # 모델 저장
    torch.save(model.state_dict(), model_path)
    print(f"Model saved to {model_path}")


# 모델 로드 함수
def load_model(model_path="model.pt"):
    global model
    try:
        # 모델 파일이 없으면 Google Drive에서 다운로드
        if not os.path.exists(model_path):
            print(f"{model_path} not found. Downloading from Google Drive...")
            download_model_from_google_drive(file_id="1GinzADjdLuuGDpOC4TXABVSpBp0ec7I4", output_path=model_path)

        # 모델 로드
        print("Loading model...")
        device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

        # 모델 객체 초기화
        #model = MultimodalModel(image_model=models.efficientnet_b0(pretrained=True), output_dim=7)

        additional_input_dim = 3  # 입력 피처 크기
        model = MultimodalModel(models.efficientnet_b0(pretrained=True), additional_input_dim, 7)

        # state_dict 로드
        state_dict = torch.load(model_path, map_location=device)
        model.load_state_dict(state_dict)
        model.eval()

        model_state = torch.load(model_path)
        print("Model loaded successfully.")
    except Exception as e:
        print(f"Error during model loading: {e}")
        raise HTTPException(status_code=500, detail=f"Model loading failed: {str(e)}")

# 이미지 전처리 함수
def image_transform(image):
    transform = transforms.Compose(
        [
            transforms.Resize((224, 224)),
            transforms.ToTensor(),
            transforms.Normalize((0.485, 0.456, 0.406), (0.229, 0.224, 0.225)),
        ]
    )
    image = Image.open(image).convert("RGB")
    return transform(image).unsqueeze(0)

def preprocess_dataframe(df):
    """
        데이터프레임에 대해 전처리 수행:\n
        - 범주형 변수 원핫 인코딩\n
        - 기존 범주형 컬럼 제거\n
        - 연속형 변수 스케일링
    """
    # 원핫 인코딩 적용할 컬럼
    categorical_cols = ['breed', 'gender', 'lesions']
    df = pd.get_dummies(df, columns=categorical_cols, drop_first=False)

    # 스케일링 적용할 컬럼
    scaler = MinMaxScaler()
    if 'age' in df.columns:
        df['age'] = scaler.fit_transform(df[['age']])

    # 모든 데이터를 float32로 변환
    df = df.astype('float32')
    return df

async def get_latest_pet_info():
    """Fetch the latest pet information from the /mine API"""
    async with AsyncClient() as client:
        response = await client.get("http://localhost:8000/mine")  # Adjust the URL if necessary
        if response.status_code == 200:
            pet_data = response.json()
            if not pet_data:
                raise HTTPException(status_code=404, detail="No pet data found.")
            latest_pet = pet_data[-1]  # 최신 데이터 선택
            return latest_pet
        else:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch pet information.")



@router.post("/predict/{pet_id}")
async def predict_api(
        pet_id: int,
        image: UploadFile = File(...),
        pet_info: PetDetailResponse = Depends(get_pet),
        db: Session = Depends(get_db)
):
    try:
        # 디버깅 로그 추가
        print(f"Pet Info: {pet_info}")
        print(f"Pet ID: {pet_id}")

        if model is None:
            print("Model is None!")
            raise HTTPException(status_code=500, detail="Model is not loaded properly.")

        # 펫 정보 추출 및 전처리
        df = pd.DataFrame([{
            "breed": pet_info.breed,
            "gender": pet_info.gender,
            "lesions": None,  # 필요한 경우 기본값 설정
            "age": pet_info.age
        }])  # PetDetailResponse를 DataFrame으로 변환
        processed_features = preprocess_dataframe(df)
        additional_features_tensor = torch.tensor(processed_features.values, dtype=torch.float32)

        # 이미지 처리
        image_tensor = image_transform(image.file)
        print(f"Input tensor shape: {image_tensor.shape}")

        # # 추가 특징 처리
        # if request and request.additional_features:
        #     additional_features_tensor = torch.tensor(request.additional_features, dtype=torch.float32).unsqueeze(0)
        # else:
        #     raise HTTPException(status_code=400, detail="Additional features are required.")

        # 모델 예측
        with torch.no_grad():
            logits = model(image_tensor, additional_features_tensor)
            probabilities = F.softmax(logits, dim=1).squeeze(0).tolist()  # 1D 리스트로 변환
            print("Logits with features:", logits)
            print("Probabilities with features:", probabilities)

        # 가장 높은 클래스 인덱스 및 이름 가져오기
        class_mapping = {
                0: "A1 구진/플라크",
                1: "A2 비듬/각질/상피성잔고리",
                2: "A3 태선화 과다색소침착",
                3: "A4 농포/여드름",
                4: "A5 미란/궤양",
                5: "A6 결정/종괴",
                6: "A7 무증상"
        }
        predicted_class_index = torch.argmax(torch.tensor(probabilities)).item()  # 가장 높은 인덱스
        predicted_class_label = class_mapping[predicted_class_index]

        # 모델 이름 (예: EfficientNet)
        model_name = "MultimodalModel-EfficientNetB0"

        # 결과를 데이터베이스에 저장
        analysis_id = save_prediction_to_db(
            session=db,
            pet_id=pet_id,
            model_name=model_name,
            probabilities=probabilities
        )

        # JSON 형식으로 결과 반환
        response = {
            "analysis_id": analysis_id,
            "analysis_date": datetime.utcnow().strftime("%Y-%m-%d"),
            "predicted_class_label": predicted_class_label,
            "A1": probabilities[0],
            "A2": probabilities[1],
            "A3": probabilities[2],
            "A4": probabilities[3],
            "A5": probabilities[4],
            "A6": probabilities[5],
            "A7": probabilities[6]
        }
        return response

    except Exception as e:
        print(f"Error during prediction: {e}")
        raise HTTPException(status_code=500, detail=str(e))


# 서버 시작 시 모델 로드
try:
    load_model("model.pt")
except Exception as e:
    print(f"Error during model setup: {e}")


def debug_model_parameters(model):
    print("\n--- Model Parameters ---")
    for name, param in model.named_parameters():
        print(f"Name: {name}")
        print(f"Shape: {param.shape}")
        print(f"Requires Grad: {param.requires_grad}")
    print("\n--- Model Buffers ---")
    for name, buffer in model.named_buffers():
        print(f"Name: {name}")
        if buffer.dim() == 0:  # 스칼라인지 확인
            print(f"Data: {buffer.item()}")  # 스칼라 출력
    print("\n--- Model State Dict ---")
    for key, value in model.state_dict().items():
        print(f"Key: {key}")
        print(f"Shape: {value.shape}")


def save_prediction_to_db(session: Session, pet_id, model_name, probabilities):
    """
    Save prediction results to the database.
    """
    new_result = AIResult(
        model_name=model_name,
        accuracy=max(probabilities),  # Use the highest probability as a proxy for accuracy
        A1=probabilities[0],
        A2=probabilities[1],
        A3=probabilities[2],
        A4=probabilities[3],
        A5=probabilities[4],
        A6=probabilities[5],
        A7=probabilities[6],
    )
    session.add(new_result)
    session.commit()
    session.refresh(new_result)  # Retrieve the inserted record with auto-incremented ID
    return new_result.analysis_id