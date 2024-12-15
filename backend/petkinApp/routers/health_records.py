from fastapi import Depends, HTTPException, APIRouter, status, Path, Query
from sqlalchemy.orm import Session
from sqlalchemy import Date, extract
from datetime import datetime
from petkinApp.models import HealthRecord  # HealthRecord 모델
from petkinApp.schemas.health_records import HealthRecordCreateRequest, HealthRecordCreateResponse, HealthRecordDetailResponse, HealthRecordDetailByDateResponse, HealthRecordDetailByMonthResponse
from petkinApp.database import get_db
from petkinApp.security import decode_jwt_token  # JWT 인증 처리
import pytz

"""
이 파일은 health-record-controller 를 위한 파일로 API 의 health-record 부분에 해당하는 API 를 구현합니다.
"""

# 라우터 설정
router = APIRouter(tags=["health-records-controller"])

@router.post("/health-records", response_model=HealthRecordCreateResponse, status_code=status.HTTP_201_CREATED)
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

    # KST 시간대 설정
    kst_timezone = pytz.timezone('Asia/Seoul')
    kst_time = datetime.now(kst_timezone)

    # 건강 기록 생성
    new_record = HealthRecord(
        pet_id=request.pet_id,
        item_id=request.item_id,
        memo=request.memo,
        timestamp=kst_time,
    )
    db.add(new_record)
    db.commit()
    db.refresh(new_record)

    # 응답 반환
    return HealthRecordCreateResponse(record_id=new_record.record_id)

@router.get("/health-records/{record_id}", response_model=HealthRecordDetailResponse, status_code=200)
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

@router.get("/pets/{pet_id}/health-records/date", response_model=list[HealthRecordDetailByDateResponse], status_code=200)
async def get_health_records_by_date(
    pet_id: int = Path(..., gt=0, description="반려동물의 ID"),
    date: str = Query(..., description="조회할 날짜 (YYYY-MM-DD) 형식"),
    token: dict = Depends(decode_jwt_token),
    db: Session = Depends(get_db),
):
    """
    특정 날짜의 반려동물 건강 기록 조회 API
    """
    # 날짜 형식 유효성 검사
    try:
        parsed_date = datetime.strptime(date, "%Y-%m-%d")
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Invalid date format. Expected YYYY-MM-DD.",
        )

    # JWT에서 사용자 인증 정보 확인
    user_id = token.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    # DB에서 해당 반려동물 ID와 날짜에 해당하는 건강 기록 조회
    records = (
        db.query(HealthRecord)
        .filter(
            HealthRecord.pet_id == pet_id,
            HealthRecord.timestamp.cast(Date) == parsed_date.date(),  # SQLAlchemy 날짜 필터링
        )
        .all()
    )

    if not records:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="No health records found for the given pet and date",
        )

    # 응답 반환
    return [
        HealthRecordDetailByDateResponse(
            record_id=record.record_id,
            item_id=record.item_id,
            memo=record.memo,
            timestamp=record.timestamp.strftime("%Y-%m-%d"),
        )
        for record in records
    ]

@router.get("/pets/{pet_id}/health-records/month", response_model=list[HealthRecordDetailByMonthResponse], status_code=200)
async def get_health_records_by_month(
    pet_id: int = Path(..., gt=0, description="반려동물의 ID"),
    month: str = Query(..., description="조회할 월 (YYYY-MM) 형식"),
    token: dict = Depends(decode_jwt_token),
    db: Session = Depends(get_db),
):
    """
    특정 달의 반려동물 건강 기록 조회 API
    """
    # 날짜 형식 유효성 검사
    try:
        parsed_date = datetime.strptime(month, "%Y-%m")
    except ValueError:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Invalid date format. Expected YYYY-MM.",
        )

    # JWT에서 사용자 인증 정보 확인
    user_id = token.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    # DB에서 해당 반려동물 ID와 월에 해당하는 건강 기록 조회
    records = (
            db.query(HealthRecord)
            .filter(
                HealthRecord.pet_id == pet_id,
                extract('year', HealthRecord.timestamp) == parsed_date.year,
                extract('month', HealthRecord.timestamp) == parsed_date.month
            )
            .all()
    )

    if not records:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="No health records found for the given pet and date",
        )

    # 응답 반환
    return [
        HealthRecordDetailByMonthResponse(
            record_id=record.record_id,
            item_id=record.item_id,
            date=record.timestamp.strftime("%Y-%m-%d"),
        )
        for record in records
    ]
