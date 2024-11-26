import os
import jwt
from fastapi import Depends, HTTPException, APIRouter
import requests
from datetime import datetime, timedelta
from dotenv import load_dotenv

"""
이 파일은 카카오 OAuth 로그인을 처리하고 JWT 토큰을 발급하는 API를 구현

파일 사용 방법:
1. `.env` 파일에 `SECRET_KEY`를 설정합니다.
"""


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
async def kakao_login(oauthAccessToken: str):
    """
    카카오 OAuth 로그인 처리\n
    :param oauthAccessToken: 클라이언트에서 전달받은 카카오 액세스 토큰\n
    :return: JWT 액세스 토큰 및 리프레시 토큰
    """
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

    # 응답 반환
    return {"accessToken": access_token, "refreshToken": refresh_token}


def create_jwt_token(data: dict, expires_delta: timedelta = timedelta(minutes=30)):
    """
    JWT 토큰 생성 함수
    :param data: JWT 페이로드 데이터
    :param expires_delta: 토큰 만료 시간 (기본값: 30분)
    :return: 생성된 JWT 토큰
    """
    to_encode = data.copy()
    expire = datetime.utcnow() + expires_delta
    to_encode.update({"exp": expire})  # 만료 시간 추가
    return jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)


def decode_jwt_token(token: str):
    """
    JWT 토큰 검증 및 디코딩
    :param token: 검증할 JWT 토큰
    :return: 디코딩된 데이터
    """
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        return payload
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="토큰이 만료되었습니다.")
    except jwt.InvalidTokenError:
        raise HTTPException(status_code=401, detail="유효하지 않은 토큰입니다.")
