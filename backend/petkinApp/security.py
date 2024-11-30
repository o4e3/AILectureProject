from fastapi.security import APIKeyHeader
from fastapi import HTTPException

class BearerTokenHeader(APIKeyHeader):
    def __init__(self, name: str = "Authorization"):
        super().__init__(name=name)

    async def __call__(self, authorization: str):
        if authorization.startswith("Bearer "):
            return authorization.split(" ")[1]  # "Bearer " 제거
        # Bearer 키워드 없이 전달된 경우 허용
        elif authorization:
            return authorization
        else:
            raise HTTPException(status_code=401, detail="Authorization 헤더가 비어있습니다.")
