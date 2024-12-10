from fastapi import Depends, HTTPException, APIRouter, status, Path
from sqlalchemy.orm import Session
from datetime import datetime
from petkinApp.models import HealthRecord  # HealthRecord 모델
from petkinApp.schemas.health_records import HealthRecordCreateRequest, HealthRecordCreateResponse, HealthRecordDetailResponse
from petkinApp.database import get_db
from petkinApp.security import decode_jwt_token  # JWT 인증 처리

"""
이 파일은 health-record-controller 를 위한 파일로 API 의 health-record 부분에 해당하는 API 를 구현합니다.
"""

# 라우터 설정
router = APIRouter(prefix="/health-records", tags=["health-records-controller"])

@router.post("/", response_model=HealthRecordCreateResponse, status_code=status.HTTP_201_CREATED)
async def create_health_record(
    request: HealthRecordCreateRequest,
    token: dict = Depends(decode_jwt_token),  # JWT 인증
    db: Session = Depends(get_db),
):
    """
    건강 기록 생성 API
    """
    # JWT에서 사용자 인증 정보 확인
    user_id = token.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    # 건강 기록 생성
    new_record = HealthRecord(
        pet_id=request.pet_id,
        item_id=request.item_id,
        memo=request.memo,
        timestamp=datetime.utcnow(),
    )
    db.add(new_record)
    db.commit()
    db.refresh(new_record)

    # 응답 반환
    return HealthRecordCreateResponse(record_id=new_record.record_id)

@router.get("/{record_id}", response_model=HealthRecordDetailResponse, status_code=200)
async def get_health_record(
    record_id: int,
    token: dict = Depends(decode_jwt_token),  # JWT 인증
    db: Session = Depends(get_db)
):
    """
    건강 기록 조회 API
    """
    # DB에서 record_id로 건강 기록 검색
    record = db.query(HealthRecord).filter(HealthRecord.record_id == record_id).first()
    if not record:
        raise HTTPException(status_code=404, detail="No health records found for the given pet")

    # 응답 반환
    return HealthRecordDetailResponse(
        record_id=record.record_id,
        item_id=record.item_id,
        memo=record.memo,
        timestamp=record.timestamp,
    )
