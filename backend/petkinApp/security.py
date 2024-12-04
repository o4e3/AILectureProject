import jwt
from fastapi.security import APIKeyHeader
from fastapi import HTTPException, Depends, Header
from dotenv import load_dotenv
import os

# .env 파일 로드
load_dotenv()

# 환경 변수에서 시크릿 키 가져오기
SECRET_KEY = os.getenv("SECRET_KEY", "default-secret-key")
ALGORITHM = "HS256"

if not SECRET_KEY or SECRET_KEY == "default-secret-key":
    raise ValueError("환경 변수에 'SECRET_KEY'가 설정되어 있지 않습니다. .env 파일을 확인하세요.")


class BearerTokenHeader(APIKeyHeader):
    def __init__(self, name: str = "Authorization"):
        super().__init__(name=name)

    async def __call__(self, authorization: str | None = None):
        if not authorization:
            raise HTTPException(status_code=401, detail="Authorization 헤더가 비어있습니다.")
        if authorization.startswith("Bearer "):
            return authorization.split(" ")[1]  # "Bearer " 제거
        raise HTTPException(status_code=401, detail="유효하지 않은 Authorization 헤더입니다.")


api_key_header = BearerTokenHeader(name="Authorization")

async def decode_jwt_token(authorization: str = Header(...)):
    """
    JWT 토큰 검증 및 디코딩
    """
    if not authorization.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="유효하지 않은 Authorization 헤더입니다.")
    token = authorization.split(" ")[1]
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        return payload
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="토큰이 만료되었습니다.")
    except jwt.InvalidTokenError as e:
        raise HTTPException(status_code=401, detail=f"유효하지 않은 토큰입니다: {str(e)}")
