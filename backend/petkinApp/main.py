from fastapi import FastAPI
from fastapi.openapi.utils import get_openapi
from petkinApp.routers import customers, pets, health_records, prediction

app = FastAPI(
    title="Petkin API",
    description="API for managing pet-related services",
    version="1.0.0",
)

# 라우터 등록
app.include_router(customers.router, prefix="/api", tags=["customers-controller"])
app.include_router(pets.router, prefix="/api", tags=["pets-controller"])
app.include_router(prediction.router, prefix="/api", tags=["prediction-controller"])
app.include_router(health_records.router, prefix="/api", tags=["health-records-controller"])

# OpenAPI 스키마 수정
def custom_openapi():
    if app.openapi_schema:
        return app.openapi_schema
    openapi_schema = get_openapi(
        title="Petkin API",
        version="1.0.0",
        description="API documentation for Petkin",
        routes=app.routes,
    )
    # BearerAuth 보안 설정
    openapi_schema["components"]["securitySchemes"] = {
        "BearerAuth": {
            "type": "http",
            "scheme": "bearer",
            "bearerFormat": "JWT"
        }
    }
    openapi_schema["security"] = [{"BearerAuth": []}]
    for path, methods in openapi_schema["paths"].items():
        for method, details in methods.items():
            if path == "/api/customers/oauth/login/KAKAO":  # 로그인 엔드포인트 경로
                for method in methods.values():
                    if "security" in method:
                        del method["security"]
            if "parameters" in details:
                # Authorization 쿼리 파라미터 제거
                details["parameters"] = [
                    param for param in details["parameters"]
                    if param.get("name") != "authorization"
                ]
    app.openapi_schema = openapi_schema
    return app.openapi_schema


app.openapi = custom_openapi
