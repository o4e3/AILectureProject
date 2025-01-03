from datetime import datetime
from sqlalchemy import DateTime, Text
from petkinApp.database import Base
from sqlalchemy import Column, Float, BigInteger, Integer, String, Boolean, ForeignKey

class HealthRecord(Base):
    __tablename__ = 'HealthRecord'
    record_id = Column(BigInteger, primary_key=True, autoincrement=True) #자동 증가 설정
    pet_id = Column(BigInteger, nullable=False)
    item_id = Column(Integer, nullable=False)
    memo = Column(Text, nullable=True)
    timestamp = Column(DateTime, default=datetime.utcnow, nullable=True)

class Customers(Base):
    __tablename__ = 'Customers'  # MySQL 테이블 이름
    customer_id = Column(BigInteger, primary_key=True)
    nickname = Column(String(100), nullable=True)
    email = Column(String(255), nullable=True, unique=True, comment="unique")
    refresh_token = Column(String(255), nullable=True)
    auth_provider = Column(String(255), nullable=True)
    registration_date = Column(DateTime, default=datetime.utcnow, nullable=True)


class Pets(Base):
    __tablename__ = 'pets'
    pet_id = Column(BigInteger, primary_key=True, autoincrement=True)
    name = Column(String(255), nullable=False)
    species = Column(String(50), nullable=False)  # dog, cat
    breed = Column(String(255), nullable=False)
    age = Column(Integer, nullable=False)
    gender = Column(String(10), nullable=False)  # M, F
    registration_date = Column(DateTime, default=datetime.utcnow, nullable=False)
    owner_id = Column(BigInteger, ForeignKey('Customers.customer_id'), nullable=False)


class AIResult(Base):
    __tablename__ = 'AIResult'
    analysis_id = Column(BigInteger, primary_key=True, autoincrement=True)  # Auto Increment
    model_name = Column(String(255), nullable=True)
    accuracy = Column(Float, nullable=True)  # Model accuracy
    analysis_date = Column(DateTime, default=datetime.utcnow, nullable=False)
    A1 = Column(Float, nullable=False)
    A2 = Column(Float, nullable=False)
    A3 = Column(Float, nullable=False)
    A4 = Column(Float, nullable=False)
    A5 = Column(Float, nullable=False)
    A6 = Column(Float, nullable=False)
    A7 = Column(Float, nullable=False)

class DiseasePredictionRecord(Base):
    __tablename__ = "DiseasePredictionRecord"
    record_id = Column(BigInteger, primary_key=True, autoincrement=True)
    pet_id = Column(BigInteger, ForeignKey("pets.pet_id"), nullable=False)
    disease_id = Column(BigInteger, nullable=False)  # 연결된 라벨
    analysis_id = Column(BigInteger, ForeignKey("AIResult.analysis_id"), nullable=False)
    image_url = Column(String(255), nullable=False)
    timestamp = Column(DateTime, default=datetime.utcnow, nullable=False)