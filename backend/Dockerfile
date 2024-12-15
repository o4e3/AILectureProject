FROM ubuntu:latest
LABEL authors="aengzu"

FROM python:3.11.0

WORKDIR /code/root

RUN pip install --upgrade pip

COPY ./requirements.txt /code/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

COPY ./petkinApp /code/root

EXPOSE 8080

CMD ["gunicorn", "petkinApp.main:app", "-k", "uvicorn.workers.UvicornWorker", "--bind", "0.0.0.0:8080", "-w", "4"]
# CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8080"]   // 애플리케이션 실행 cmd : uvicorn main:app --host 0.0.0.0 --port 8081