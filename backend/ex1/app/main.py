from fastapi import FastAPI
from app.auth import routes

app = FastAPI()

# 라우터 추가
app.include_router(routes.router)
