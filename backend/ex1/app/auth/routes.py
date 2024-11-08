from fastapi import APIRouter, Depends, HTTPException
from app.auth.kakao_oauth import KakaoOAuth2
from fastapi.responses import RedirectResponse
from fastapi.security import OAuth2AuthorizationCodeBearer
import os

router = APIRouter()
kakao_oauth_client = KakaoOAuth2()

@router.get("/auth/kakao/login")
async def login():
    authorization_url = kakao_oauth_client.get_authorization_url()
    return RedirectResponse(authorization_url)

@router.get("/auth/kakao/callback")
async def callback(code: str):
    token = await kakao_oauth_client.get_access_token(code)
    user_id, email = await kakao_oauth_client.get_id_email(token)

    if not email:
        raise HTTPException(status_code=400, detail="Email not found")

    return {"user_id": user_id, "email": email}
