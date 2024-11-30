import os

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, Session
from sqlalchemy.ext.declarative import declarative_base
from dotenv import load_dotenv

"""
TODO : DB 연결을 위한 파일
--------------------------------------------
이 파일은 SQLAlchemy를 사용하여 데이터베이스와 연결을 설정 + ORM 모델의 기본 클래스 및 데이터베이스 세션을 관리

- SQLALCHEMY_DATABASE_URL:
  데이터베이스 URL을 정의한다
  현재 설정은 SQLite 데이터베이스 파일(`./todosapp.db`)(로컬 db)을 사용하고 있으나
  MySQL 로 변경 할 때는 URL을 수정해야됨
  예시(다를 수 있으니 설정에 맞게):
  - MySQL: 'mysql+pymysql://root:rhaxod0820@host:3306/petkin_db' 
"""

# 환경 변수 로드
load_dotenv()

DATABASE_URL = os.getenv("DATABASE_URL")
engine = create_engine(DATABASE_URL)

# 세션 생성: 자동 커밋 및 자동 플러시는 비활성화
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

#  ORM 기본 클래스: 모델 정의 시 Base를 상속받아 사용
Base = declarative_base()


# 데이터베이스 세션 Dependency
def get_db() -> Session:
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
