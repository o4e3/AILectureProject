from pydantic import BaseModel, Field
class RefreshTokenRequest(BaseModel):
    refreshToken: str