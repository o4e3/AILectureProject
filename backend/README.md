## 🐧 프로젝트 초기 설정

`requirements.txt`에 사용된 패키지를 정리하였습니다.  
버전이 다르면 충돌이 발생할 수 있으므로 반드시 해당 버전을 사용하세요.

---

### 1. 가상 환경 생성 (필수)
> 가상환경이 이미 있다면 해당 폴더를 삭제한 후 진행하세요.
```bash
python -m venv fastapi_env
```
위 명령어를 사용하여 `fastapi_env`라는 이름의 가상 환경을 생성합니다.

---

### 2. 가상 환경 활성화 (필수)
- **macOS / Linux**:
  ```bash
  source fastapi_env/bin/activate
  ```
- **Windows (cmd)**:
  ```cmd
  fastapi_env\Scripts\activate
  ```
- **Windows (PowerShell)**:
  ```powershell
  .\fastapi_env\Scripts\Activate.ps1
  ```

---

### 3. 패키지 설치 (필수)
가상 환경을 활성화한 상태에서 `requirements.txt`에 명시된 기존 패키지를 설치합니다:
```bash
pip install -r requirements.txt
```
- 여기까지 하면 기본 설정은 완료이고 아래 설정은 참고할 것들
---

### 4. 가상 환경 비활성화 (선택)
가상 환경 사용이 끝난 후 비활성화합니다:
```bash
deactivate
```

---

### 5. 추가 패키지 설치 (선택)
- 혹시라도 새로운 패키지를 설치하게 된다면 이런식으로 하면 됩니다.
- 새로운 패키지를 설치한 후 `requirements.txt`에 반영하려면 아래 명령어를 실행합니다:
```bash
pip freeze > requirements.txt
```

이후 팀원과 공유하거나 다른 환경에서 설치 시 다음 명령어를 실행하세요:
```bash
pip install -r requirements.txt
```

---

위 단계를 따라하면 프로젝트 환경 구성이 완료됩니다. 😊



## 🙈 폴더 설명

- backend 폴더 내에 `petkinApp` 이라는 패키지가 작업을 하게될 패키지입니다.
- 그 내부의 `routers` 라는 패키지가 라우터로 분리하여 API 생성 코드 작성하면 됩니다.
- `database.py` 에서 데이터베이스 연동을 진행합니다.
- `models.py` 에서 데이터베이스 테이블의 ORM 모델을 작성합니다.
- 파일 구조는 다음과 같습니다.
  <br>
  <img width="448" alt="스크린샷 2024-11-26 오전 11 18 41" src="https://github.com/user-attachments/assets/530fd97d-fa86-46e0-9ec7-e99d7b8dcbfd">


- 실행 시 backend 폴더의 위치에서 `uvicorn petkinApp.main:app --reload` 을 실행하여 시작합니다.

<img width="767" alt="스크린샷 2024-11-26 오전 11 19 20" src="https://github.com/user-attachments/assets/78810788-c2e4-4f56-9bed-928efc58ad25">
