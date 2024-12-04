import os
import jwt
from fastapi import Depends, HTTPException, APIRouter, Body
import requests
from datetime import datetime, timedelta
from dotenv import load_dotenv
from sqlalchemy.orm import Session
from pydantic import BaseModel
from petkinApp.database import get_db
from petkinApp.models import Customers
from fastapi.security import OAuth2PasswordBearer

from petkinApp.schemas.customers import RefreshTokenRequest
from petkinApp.security import decode_jwt_token  # Import from security.py

# OAuth2PasswordBearer 설정
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/api/customers/oauth/login/KAKAO")

# Pydantic 모델 정의
class KakaoTokenRequest(BaseModel):
    oauthAccessToken: str

# 라우터 설정
router = APIRouter(prefix="/customers")

# .env 파일 로드
load_dotenv()

# 환경 변수에서 시크릿 키 가져오기
SECRET_KEY = os.getenv("SECRET_KEY", "default-secret-key")
ALGORITHM = "HS256"

if not SECRET_KEY or SECRET_KEY == "default-secret-key":
    raise ValueError("환경 변수에 'SECRET_KEY'가 설정되어 있지 않습니다. .env 파일을 확인하세요.")


@router.post("/oauth/login/KAKAO")
async def kakao_login(token_request: KakaoTokenRequest, db: Session = Depends(get_db)):
    """
    카카오 OAuth 로그인 처리
    """
    oauthAccessToken = token_request.oauthAccessToken
    KAKAO_USERINFO_API_URL = "https://kapi.kakao.com/v2/user/me"
    headers = {"Authorization": f"Bearer {oauthAccessToken}"}

    # 카카오 사용자 정보 요청
    response = requests.get(KAKAO_USERINFO_API_URL, headers=headers)
    if response.status_code != 200:
        raise HTTPException(
            status_code=400,
            detail=f"카카오 액세스 토큰 검증 실패: {response.text}"
        )

    # 카카오 사용자 정보 파싱
    user_info = response.json()
    kakao_id = user_info["id"]
    nickname = user_info["properties"]["nickname"]
    email = user_info.get("kakao_account", {}).get("email")

    # JWT 생성
    access_token = create_jwt_token({"sub": kakao_id, "nickname": nickname})
    refresh_token = create_jwt_token({"sub": kakao_id, "type": "refresh"}, timedelta(days=7))

    # 고객 정보 업데이트 또는 삽입
    upsert_customer(db, kakao_id, nickname, email, refresh_token)

    # 응답 반환
    return {"accessToken": access_token, "refreshToken": refresh_token}


def create_jwt_token(data: dict, expires_delta: timedelta = timedelta(minutes=30)):
    """
    JWT 토큰 생성 함수
    """
    to_encode = data.copy()
    expire = datetime.utcnow() + expires_delta
    to_encode.update({"exp": expire})

    token = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return token


def upsert_customer(
    db: Session,
    kakao_id: str,
    nickname: str,
    email: str,
    refresh_token: str
):
    """
    고객 정보를 삽입하거나 업데이트
    """
    try:
        customer = db.query(Customers).filter(Customers.email == email).first()
        if customer:
            customer.customer_id = kakao_id
            customer.nickname = nickname
            customer.refresh_token = refresh_token
            customer.auth_provider = "KAKAO"
        else:
            new_customer = Customers(
                customer_id=kakao_id,
                nickname=nickname,
                email=email,
                refresh_token=refresh_token,
                auth_provider="KAKAO",
            )
            db.add(new_customer)

        db.commit()
    except Exception as e:
        db.rollback()
        raise HTTPException(status_code=500, detail=f"DB 작업 실패: {str(e)}")


@router.get("/me")
async def get_my_profile(token: dict = Depends(decode_jwt_token), db: Session = Depends(get_db)):
    """
    내 정보 보기 API
    """
    kakao_id = token.get("sub")
    customer = db.query(Customers).filter(Customers.customer_id == kakao_id).first()

    if not customer:
        raise HTTPException(status_code=404, detail="사용자 정보를 찾을 수 없습니다.")

    return {
        "customer_id": customer.customer_id,
        "nickname": customer.nickname,
        "email": customer.email,
        "registration_date": customer.registration_date
    }

@router.post("/token/refresh")
async def refresh_token(
    refresh_token_request: RefreshTokenRequest,  # 변경된 부분
    db: Session = Depends(get_db)
):
    """
    토큰 리프레시 API
    """
    # 요청에서 refreshToken 추출
    refresh_token = refresh_token_request.refreshToken
    if not refresh_token:
        raise HTTPException(status_code=400, detail="Refresh token이 제공되지 않았습니다.")

    # Refresh token 검증
    try:
        payload = jwt.decode(refresh_token, SECRET_KEY, algorithms=[ALGORITHM])
        kakao_id = payload.get("sub")
        token_type = payload.get("type")

        if not kakao_id or token_type != "refresh":
            raise HTTPException(status_code=400, detail="유효하지 않은 토큰 형식입니다.")

    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="Refresh token이 만료되었습니다.")
    except jwt.InvalidTokenError as e:
        raise HTTPException(status_code=401, detail=f"유효하지 않은 토큰입니다: {str(e)}")

    # DB에서 사용자 확인
    customer = db.query(Customers).filter(Customers.customer_id == kakao_id).first()
    if not customer or customer.refresh_token != refresh_token:
        raise HTTPException(status_code=401, detail="유효하지 않은 토큰입니다.")

    # 새로운 accessToken 및 refreshToken 생성
    access_token = create_jwt_token({"sub": kakao_id, "nickname": customer.nickname})
    new_refresh_token = create_jwt_token({"sub": kakao_id, "type": "refresh"}, timedelta(days=7))

    # Refresh token 업데이트
    customer.refresh_token = new_refresh_token
    db.commit()

    # 응답 반환
    return {"accessToken": access_token, "refreshToken": new_refresh_token}
