# Python 3.11 이미지를 베이스로 설정
FROM python:3.11-slim


# 환경 변수 설정
ENV PYTHONUNBUFFERED=1

# 작업 디렉토리 설정
WORKDIR /app

# 시스템 의존성 설치 (PyTorch 및 MySQL 연동용)
RUN apt-get update && \
    apt-get install -y --no-install-recommends gcc libpq-dev libssl-dev libffi-dev && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Python 의존성 설치
COPY ./requirements.txt /app/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /app/requirements.txt

# 애플리케이션 및 기타 파일 복사
COPY ./petkinApp /app/petkinApp
COPY ./model.pt /app/model.pt
COPY ./static /app/static
COPY .env /app/.env


# 포트 노출
EXPOSE 8080

# Gunicorn 실행
CMD ["gunicorn", "petkinApp.main:app", "-k", "uvicorn.workers.UvicornWorker", "--bind", "0.0.0.0:8080", "-w", "1"]

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
    CMD curl -f http://localhost:8080/health || exit 1