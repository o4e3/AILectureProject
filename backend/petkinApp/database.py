from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

"""
TODO : DB 연결을 위한 파일
--------------------------------------------
이 파일은 SQLAlchemy를 사용하여 데이터베이스와 연결을 설정 + ORM 모델의 기본 클래스 및 데이터베이스 세션을 관리

- SQLALCHEMY_DATABASE_URL:
  데이터베이스 URL을 정의한다
  현재 설정은 SQLite 데이터베이스 파일(`./todosapp.db`)(로컬 db)을 사용하고 있으나
  MySQL 로 변경 할 때는 URL을 수정해야됨
  예시(다를 수 있으니 설정에 맞게):
  - MySQL: 'mysql+pymysql://username:password@host:port/database' 
"""

# TODO 여기에 sql 주소 넣어서 Mysql 연동하기
SQLALCHEMY_DATABASE_URL = 'sqlite:///./todosapp.db'

# 기본적으로 SQLite 는 하나의 스레드만 통신을 허용한다.
# 다른 종류의 요청에 대해 같은 연결을 공유할 때 발생하는 문제 방지하고자 check_same_thread 사용
engine = create_engine(
    SQLALCHEMY_DATABASE_URL,
    connect_args={'check_same_thread': False}  # Corrected parameter
)

# 세션 생성: 자동 커밋 및 자동 플러시는 비활성화
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

#  ORM 기본 클래스: 모델 정의 시 Base를 상속받아 사용
Base = declarative_base()
