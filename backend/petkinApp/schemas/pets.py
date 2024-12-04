from pydantic import BaseModel, Field
from datetime import datetime

class PetRegisterRequest(BaseModel):
    name: str = Field(..., max_length=255)
    species: str = Field(..., regex="^(dog|cat)$")
    breed: str = Field(..., max_length=255)
    age: int = Field(..., ge=0)  # 나이는 0 이상
    gender: str = Field(..., regex="^(M|F)$")

class PetRegisterResponse(BaseModel):
    pet_id: int
    name: str
    species: str
    breed: str
    age: int
    gender: str
    registration_date: datetime

class PetDetailResponse(BaseModel):
    pet_id: int
    name: str
    species: str
    breed: str
    age: int
    gender: str
    registration_date: datetime

class PetUpdateRequest(BaseModel):
    name: str = Field(..., max_length=255)
    species: str = Field(..., regex="^(dog|cat)$")
    breed: str = Field(..., max_length=255)
    age: int = Field(..., ge=0)  # 나이는 0 이상
    gender: str = Field(..., regex="^(M|F)$")