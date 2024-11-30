from fastapi import FastAPI
from petkinApp import models
from petkinApp.database import engine
from petkinApp.routers import customers
from petkinApp.routers import pets
from fastapi.security import OAuth2PasswordBearer


"""
이 파일은 FastAPI 애플리케이션의 진입점, FastAPI 인스턴스를 생성 + 라우터 포함
"""

app = FastAPI()  # FastAPI 애플리케이션 인스턴스 생성


# 이는 애플리케이션 시작 시 데이터베이스 스키마를 초기화하는 데 사용됩니다.
models.Base.metadata.create_all(bind=engine)

# OAuth2PasswordBearer 설정
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/api/customers/oauth/login/KAKAO")

# TODO 여기에 이런 식으로 라우터 포함 시키기
# app.include_router(todos.router, prefix='/api', tags=["todos-controller"])
app.include_router(customers.router, prefix='/api', tags=["customers-controller"])
app.include_router(pets.router, prefix='/api', tags=["pets-controller"])
app.include_router(pets.router, prefix='/api', tags=["health-records-controller"])