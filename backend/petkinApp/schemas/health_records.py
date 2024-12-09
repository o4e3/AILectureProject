from pydantic import BaseModel, Field
from datetime import datetime

#Field를 사용한 유효성 검사, ...은 필수 필드임을 의미
class HealthRecordCreateRequest(BaseModel):
    pet_id: int = Field(..., gt=0) #pet_id와 item_id는 양수
    item_id: int = Field(..., gt=0)
    memo: str = Field(..., max_length=500) #memo 최대 길이 설정으로 과도한 입력 방지

class HealthRecordCreateResponse(BaseModel):
    record_id: int = Field(..., )

class HealthRecordDetailResponse(BaseModel):
    record_id: int = Field(..., gt=0)
    item_id: int = Field(..., gt=0)
    memo: str = Field(..., max_length=500)
    timestamp: datetime = Field(..., )