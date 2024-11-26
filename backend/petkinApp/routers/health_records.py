from fastapi import Depends, HTTPException, Path, APIRouter


"""
이 파일은 health-record-controller 를 위한 파일로 API 의 health-record 부분에 해당하는 API 를 구현합니다.
"""

# 라우터 설정
router = APIRouter(prefix="/health-records")