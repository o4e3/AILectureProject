from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from petkinApp.database import get_db
from petkinApp.models import Pets, Customers
from datetime import datetime
from petkinApp.schemas.pets import PetRegisterRequest, PetRegisterResponse, PetDetailResponse, PetUpdateRequest
from petkinApp.security import decode_jwt_token

router = APIRouter(prefix="/pets", tags=["pets-controller"])
@router.post("/", response_model=PetRegisterResponse, status_code=status.HTTP_201_CREATED)
async def register_pet(
    request: PetRegisterRequest,
    token: dict = Depends(decode_jwt_token),  # JWT 토큰 디코딩
    db: Session = Depends(get_db)
):
    """
    펫 등록 API
    """
    # JWT에서 사용자 ID 추출
    owner_id = token.get("sub")
    if not owner_id:
        raise HTTPException(status_code=401, detail="유효하지 않은 인증 정보입니다.")

    # 사용자 확인
    owner = db.query(Customers).filter(Customers.customer_id == owner_id).first()
    if not owner:
        raise HTTPException(status_code=404, detail="사용자를 찾을 수 없습니다.")

    # 펫 저장
    new_pet = Pets(
        name=request.name,
        species=request.species,
        breed=request.breed,
        age=request.age,
        gender=request.gender,
        registration_date=datetime.utcnow(),
        owner_id=owner_id
    )
    db.add(new_pet)
    db.commit()
    db.refresh(new_pet)

    # 응답 반환
    return PetRegisterResponse(
        pet_id=new_pet.pet_id,
        name=new_pet.name,
        species=new_pet.species,
        breed=new_pet.breed,
        age=new_pet.age,
        gender=new_pet.gender,
        registration_date=new_pet.registration_date
    )


@router.get("/{pet_id}", response_model=PetDetailResponse, status_code=status.HTTP_200_OK)
async def get_pet(
    pet_id: int,
    token: dict = Depends(decode_jwt_token),  # JWT 토큰 디코딩
    db: Session = Depends(get_db)
):
    """
    펫 정보 조회 API
    """
    # JWT에서 사용자 ID 추출
    owner_id = token.get("sub")
    if not owner_id:
        raise HTTPException(status_code=401, detail="유효하지 않은 인증 정보입니다.")

    # DB에서 pet_id와 owner_id로 반려동물 조회
    pet = db.query(Pets).filter(Pets.pet_id == pet_id, Pets.owner_id == owner_id).first()
    if not pet:
        raise HTTPException(status_code=404, detail="Pet not found")

    # 응답 반환
    return PetDetailResponse(
        pet_id=pet.pet_id,
        name=pet.name,
        species=pet.species,
        breed=pet.breed,
        age=pet.age,
        gender=pet.gender,
        registration_date=pet.registration_date
    )

@router.put("/{pet_id}", response_model=PetDetailResponse, status_code=status.HTTP_200_OK)
async def update_pet(
    pet_id: int,
    request: PetUpdateRequest,
    token: dict = Depends(decode_jwt_token),  # JWT 토큰 디코딩
    db: Session = Depends(get_db)
):
    """
    반려동물 정보 수정 API
    """
    # JWT에서 사용자 ID 추출
    owner_id = token.get("sub")
    if not owner_id:
        raise HTTPException(status_code=401, detail="유효하지 않은 인증 정보입니다.")

    # DB에서 pet_id와 owner_id로 반려동물 조회
    pet = db.query(Pets).filter(Pets.pet_id == pet_id, Pets.owner_id == owner_id).first()
    if not pet:
        raise HTTPException(status_code=404, detail="Pet not found")

    # 반려동물 정보 업데이트
    pet.name = request.name
    pet.species = request.species
    pet.breed = request.breed
    pet.age = request.age
    pet.gender = request.gender
    pet.registration_date = datetime.utcnow()  # 수정된 날짜로 업데이트
    db.commit()
    db.refresh(pet)

    # 응답 반환
    return PetDetailResponse(
        pet_id=pet.pet_id,
        name=pet.name,
        species=pet.species,
        breed=pet.breed,
        age=pet.age,
        gender=pet.gender,
        registration_date=pet.registration_date
    )