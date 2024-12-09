import json
from typing import List

from fastapi import FastAPI, File, UploadFile, HTTPException, APIRouter, Form
from pydantic import BaseModel
from torch import nn
import torch
from torchvision import transforms, models
from PIL import Image

# 라우터 설정
router = APIRouter(prefix="/ai")
model = None


# 사용자 정의 모델 클래스 정의
class MultimodalModel(nn.Module):
    def __init__(self, image_model, output_dim):
        super(MultimodalModel, self).__init__()

        self.image_model = image_model

        # 추가 특징을 처리하는 MLP
        self.fc_additional = nn.Sequential(
            nn.Linear(0, 64),  # 나중에 forward에서 동적으로 설정
            nn.ReLU(),
            nn.Linear(64, 64)
        )

        # 이미지 특징과 추가 feature 결합 후 예측하는 fully connected layer
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


# 요청 모델 정의
class PredictionRequest(BaseModel):
    additional_features: List[float]


# 모델 로드 함수
def load_model(model_path):
    global model
    print("Loading model...")
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    state_dict = torch.load(model_path, map_location=device)
    model = MultimodalModel(image_model=models.efficientnet_b0(pretrained=True), output_dim=7)
    model.load_state_dict(state_dict)
    model.eval()
    print("Model loaded successfully.")


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


@router.post("/predict")
async def predict_api(
    image: UploadFile = File(...),
    request: PredictionRequest = None,
):
    try:
        # 이미지 처리
        image_tensor = image_transform(image.file)
        print(f"Input tensor shape: {image_tensor.shape}")

        # 추가 특징 처리
        if request and request.additional_features:
            additional_features_tensor = torch.tensor(request.additional_features, dtype=torch.float32).unsqueeze(0)
        else:
            raise HTTPException(status_code=400, detail="Additional features are required.")

        # 모델 예측
        with torch.no_grad():
            prediction = model(image_tensor, additional_features_tensor)
        print(f"Model output: {prediction}")
        predicted_class = prediction.argmax(dim=1).item()
        return {"predicted_class": predicted_class + 1}
    except Exception as e:
        print(f"Error during prediction: {e}")
        raise HTTPException(status_code=500, detail=str(e))


# 서버 시작 시 모델 로드
try:
    load_model("model.pt")
except Exception as e:
    print(f"Error during model setup: {e}")
