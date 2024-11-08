import os
import json
from typing import Any, Dict, List, Optional, Tuple, cast
from httpx_oauth.oauth2 import BaseOAuth2
#from httpx_oauth.errors import GetIdEmailError

AUTHORIZE_ENDPOINT = "https://kauth.kakao.com/oauth/authorize"
ACCESS_TOKEN_ENDPOINT = "https://kauth.kakao.com/oauth/token"
PROFILE_ENDPOINT = "https://kapi.kakao.com/v2/user/me"
BASE_SCOPES = ["profile_nickname", "profile_image", "account_email"]

class KakaoOAuth2(BaseOAuth2[Dict[str, Any]]):
    display_name = "Kakao"

    def __init__(self):
        super().__init__(
            client_id=os.getenv("KAKAO_CLIENT_ID"),
            client_secret=os.getenv("KAKAO_CLIENT_SECRET"),
            authorize_endpoint=AUTHORIZE_ENDPOINT,
            access_token_endpoint=ACCESS_TOKEN_ENDPOINT,
            base_scopes=BASE_SCOPES
        )
        self.redirect_uri = os.getenv("KAKAO_REDIRECT_URI")

    async def get_id_email(self, token: str) -> Tuple[str, Optional[str]]:
        async with self.get_httpx_client() as client:
            response = await client.post(
                PROFILE_ENDPOINT,
                headers={"Authorization": f"Bearer {token}"},
                params={"property_keys": json.dumps(BASE_SCOPES)}
            )



            account_info = cast(Dict[str, Any], response.json())
            kakao_account = account_info.get('kakao_account')

            return str(account_info.get('id')), kakao_account.get('email')
