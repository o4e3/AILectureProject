from fastapi import Depends, HTTPException, APIRouter, status, Path, Query, Response
from sqlalchemy.orm import Session
from sqlalchemy import Date, extract
from datetime import datetime
from petkinApp.models import HealthRecord  # HealthRecord 모델
from petkinApp.schemas.health_records import HealthRecordCreateRequest, HealthRecordCreateResponse, HealthRecordDetailResponse, HealthRecordUpdateRequest, HealthRecordDetailByDateResponse, HealthRecordDetailByMonthResponse
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
        raise HTTPException(status_code=404, detail="No health records found for the given record id")

    # 응답 반환
    return HealthRecordDetailResponse(
        record_id=record.record_id,
        item_id=record.item_id,
        memo=record.memo,
        timestamp=record.timestamp,
    )

@router.put("/health-records/{record_id}", response_model=HealthRecordDetailResponse, status_code=200)
async def update_health_record(
    request: HealthRecordUpdateRequest,
    record_id: int = Path(..., description="수정할 건강 기록의 ID"),
    token: dict = Depends(decode_jwt_token),  # JWT 인증
    db: Session = Depends(get_db)
):
    """
    건강 기록 수정 API
    """
    # JWT에서 사용자 인증 정보 확인
    user_id = token.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    # DB에서 record_id로 건강 기록 검색
    record = db.query(HealthRecord).filter(HealthRecord.record_id == record_id).first()
    if not record:
        raise HTTPException(status_code=404, detail="Health record not found")

    # 기록 수정(수정된 값만 반영)
    if request.item_id is not None:
        record.item_id = request.item_id
    if request.memo is not None:
        record.memo = request.memo
    if request.timestamp is not None:
        kst_timezone = pytz.timezone('Asia/Seoul')  # KST 시간대 객체

        if isinstance(request.timestamp, str):  # 문자열로 들어온 경우
            naive_time = datetime.strptime(request.timestamp, "%Y-%m-%dT%H:%M:%S")
        elif isinstance(request.timestamp, datetime):  # 이미 datetime 객체인 경우
            naive_time = request.timestamp
        else:
            raise HTTPException(
                status_code=400,
                detail="Invalid timestamp format."
            )

        # KST로 변환
        if naive_time.tzinfo is None:  # 시간대가 없는 경우 UTC로 가정
            utc_timezone = pytz.utc
            utc_time = naive_time.replace(tzinfo=utc_timezone)  # UTC로 설정
        else:  # 시간대가 이미 있는 경우 UTC로 변환
            utc_time = naive_time.astimezone(pytz.utc)

        # UTC에서 KST로 변환
        kst_time = utc_time.astimezone(kst_timezone)

        # DB에 저장할 값
        record.timestamp = kst_time
    db.commit()
    db.refresh(record)

    # 응답 반환
    return HealthRecordDetailResponse(
        record_id=record.record_id,
        item_id=record.item_id,
        memo=record.memo,
        timestamp=record.timestamp,
    )

@router.delete("/health-records/{record_id}", status_code=status.HTTP_200_OK)
async def delete_health_record(
    record_id: int,
    token: dict = Depends(decode_jwt_token),  # JWT 인증
    db: Session = Depends(get_db)
):
    """
    건강 기록 삭제 API
    """
    # JWT에서 사용자 인증 정보 확인
    user_id = token.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    # DB에서 record_id로 건강 기록 검색
    record = db.query(HealthRecord).filter(HealthRecord.record_id == record_id).first()
    if not record:
        raise HTTPException(status_code=404, detail="Health record not found")

    # 건강기록 삭제
    db.delete(record)
    db.commit()

    # 성공 응답 반환
    return Response(status_code=200)

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
    특정 월의 반려동물 건강 기록 조회 API
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

@router.get("/pets/{pet_id}/health-records/pet", response_model=list[HealthRecordDetailResponse], status_code=200)
async def get_pet_health_records_by_pet(
    pet_id: int = Path(..., gt=0, description="건강 기록을 조회할 반려동물의 ID"),
    token: dict = Depends(decode_jwt_token),  # JWT 인증
    db: Session = Depends(get_db),
):
    """
    특정 반려동물 건강 기록 조회 API
    """
    # JWT에서 사용자 인증 정보 확인
    user_id = token.get("sub")
    if not user_id:
        raise HTTPException(status_code=401, detail="Invalid or expired token")

    # DB에서 해당 반려동물의 건강 기록 조회
    records = db.query(HealthRecord).filter(HealthRecord.pet_id == pet_id).all()

    if not records:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="No health records found for the given pet",
        )

    # 응답 반환
    return [
        HealthRecordDetailResponse(
            record_id=record.record_id,
            item_id=record.item_id,
            memo=record.memo,
            timestamp=record.timestamp,
        )
        for record in records
    ]
