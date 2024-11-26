from datetime import datetime
from sqlalchemy import DateTime
from petkinApp.database import Base
from sqlalchemy import Column, BigInteger, Integer, String, Boolean, ForeignKey

"""
이 파일은 SQLAlchemy를 사용하여 데이터베이스 테이블의 ORM 모델을 정의하는 models 파일
DB 테이블과 동일하게 만들기 (확인하면 삭졔)
"""

# TODO 아래는 테스트 코드로 이해했으면 삭제하기!
class Todos(Base):
    __tablename__ = 'todos'
    id = Column(Integer, primary_key=True, index=True)
    title = Column(String)
    description = Column(String)
    priority = Column(Integer)
    complete = Column(Boolean, default=False)
    owner_id = Column(Integer, default=1234)



class Customers(Base):
    __tablename__ = 'Customers'  # MySQL 테이블 이름
    customer_id = Column(BigInteger, primary_key=True, autoincrement=True, comment="Primary Key, Auto Increment")
    nickname = Column(String(100), nullable=True)
    email = Column(String(255), nullable=True, unique=True, comment="unique")
    refresh_token = Column(String(255), nullable=True)
    auth_provider = Column(String(255), nullable=True)
    registration_date = Column(DateTime, default=datetime.utcnow, nullable=True)