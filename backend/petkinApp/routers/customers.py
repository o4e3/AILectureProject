import os
import jwt
from fastapi import Depends, HTTPException, APIRouter
import requests
from datetime import datetime, timedelta
from dotenv import load_dotenv
from sqlalchemy.orm import Session
from pydantic import BaseModel
from petkinApp.database import get_db
from petkinApp.models import Customers
from fastapi.security import OAuth2PasswordBearer
from fastapi.security import APIKeyHeader

from petkinApp.security import BearerTokenHeader

"""
이 파일은 카카오 OAuth 로그인을 처리하고 JWT 토큰을 발급하는 API를 구현

파일 사용 방법:
1. `.env` 파일에 `SECRET_KEY`를 설정합니다.
"""

# OAuth2PasswordBearer 설정
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/api/customers/oauth/login/KAKAO")

# 커스텀 BearerTokenHeader 사용
api_key_header = BearerTokenHeader(name="Authorization")

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
    카카오 OAuth 로그인 처리\n
    :param oauthAccessToken: 클라이언트에서 전달받은 카카오 액세스 토큰\n
    :return: JWT 액세스 토큰 및 리프레시 토큰
    """
    oauthAccessToken = token_request.oauthAccessToken  # JSON에서 추출
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
    kakao_id = user_info["id"]  # 카카오 고유 사용자 ID
    nickname = user_info["properties"]["nickname"]
    email = user_info.get("kakao_account", {}).get("email")

    # JWT 생성
    access_token = create_jwt_token({"sub": kakao_id, "nickname": nickname})
    refresh_token = create_jwt_token({"sub": kakao_id, "type": "refresh"}, timedelta(days=7))

    # 고객 정보 업데이트 또는 삽입
    upsert_customer(db, kakao_id, nickname, email, refresh_token)

    # 응답 반환
    return {"accessToken": access_token, "refreshToken": refresh_token}


# JWT 생성 시 sub 필드를 문자열로 변환
def create_jwt_token(data: dict, expires_delta: timedelta = timedelta(minutes=30)):
    """
    JWT 토큰 생성 함수
    """
    to_encode = data.copy()
    expire = datetime.utcnow() + expires_delta
    to_encode.update({"exp": expire})  # 만료 시간 추가

    # 'sub' 필드가 존재하면 문자열로 변환
    if "sub" in to_encode:
        to_encode["sub"] = str(to_encode["sub"])

    token = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)

    # 디코딩 테스트
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        print(f"Generated Token: {token}")
        print(f"Decoded Payload: {payload}")
    except Exception as e:
        print(f"Error decoding token right after generation: {e}")

    return token


def decode_jwt_token(token: str = Depends(api_key_header)):
    """
    JWT 토큰 검증 및 디코딩
    """
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        return payload
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="토큰이 만료되었습니다.")
    except jwt.InvalidTokenError as e:
        print(f"Invalid Token Error: {e}")
        raise HTTPException(status_code=401, detail=f"유효하지 않은 토큰입니다: {str(e)}")



def upsert_customer(
    db: Session,
    kakao_id: str,
    nickname: str,
    email: str,
    refresh_token: str
):
    """
    고객 정보를 삽입하거나 업데이트하는 함수
    """
    try:
        # 먼저 이메일 기준으로 기존 고객 검색
        print(f"Checking for existing customer with email: {email}")
        customer = db.query(Customers).filter(Customers.email == email).first()

        if customer:
            # 이메일이 존재하는 경우 해당 고객 정보를 업데이트
            print(f"Updating existing customer: {customer}")
            customer.customer_id = kakao_id  # customer_id도 업데이트
            customer.nickname = nickname
            customer.refresh_token = refresh_token
            customer.auth_provider = "KAKAO"
        else:
            # 신규 고객 삽입
            print(f"Inserting new customer with email: {email}")
            new_customer = Customers(
                customer_id=kakao_id,
                nickname=nickname,
                email=email,
                refresh_token=refresh_token,
                auth_provider="KAKAO",
            )
            db.add(new_customer)

        db.commit()  # 데이터 저장
    except Exception as e:
        db.rollback()
        print(f"DB 작업 실패: {str(e)}")
        raise HTTPException(status_code=500, detail=f"DB 작업 실패: {str(e)}")




@router.get("/me")
async def get_my_profile(token: dict = Depends(decode_jwt_token), db: Session = Depends(get_db)):
    """
    내 정보 보기 API
    :param token: 디코딩된 JWT 토큰 정보
    :param db: SQLAlchemy 세션
    :return: 사용자 정보
    """
    kakao_id = token.get("sub")  # JWT의 'sub' 필드 (카카오 사용자 ID)
    customer = db.query(Customers).filter(Customers.customer_id == kakao_id).first()

    if not customer:
        raise HTTPException(status_code=404, detail="사용자 정보를 찾을 수 없습니다.")

    return {
        "customer_id": customer.customer_id,
        "nickname": customer.nickname,
        "email": customer.email,
        "registration_date": customer.registration_date
    }