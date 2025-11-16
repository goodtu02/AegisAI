# AegisAI
[AegisAI 프로젝트 배너 이미지 삽입]
<br/>
<br/>

# 1. Project Overview (프로젝트 개요)
- **프로젝트 이름**: 🛡️ AegisAI
- **프로젝트 설명**: LLM과 AI를 이용한 품질 및 보안 점검 도구 서비스
- **프로젝트 목표**:
    - AI 기반으로 소스 코드의 보안 취약점을 자동으로 탐지
    - 개발자가 즉시 적용할 수 있는 안전한 수정 코드를 제안
    - 수정안에 대한 자연어 가이드를 생성하여 개발자의 이해도 향상

<br/>
<br/>

# 2. Team Members (팀원 및 팀 소개)
| 김성은 | 권태욱 | 이정재 | 황지원 |
|:------:|:------:|:------:|:------:|
| [역할, 예: PL / Backend] | [역할, 예: AI / Data] | [역할, 예: Frontend] | [역할, 예: Backend / Infra] |
| [GitHub]([https://github.com/링크]) | [GitHub]([https://github.com/링크]) | [GitHub]([https://github.com/링크]) | [GitHub]([https://github.com/링크]) |

<br/>
<br/>

# 3. Key Features (주요 기능)
- **AI 기반 자동 취약점 탐지**:
  - **GraphCodeBERT** 모델을 사용하여 코드의 문맥과 데이터 흐름을 파악하고 취약한 라인을 식별합니다.

- **안전한 코드로 자동 변환**:
  - **CodeT5** 모델이 탐지된 취약 코드를 보안성이 강화된 안전한 코드로 자동 변환하고 수정안을 제안합니다.

- **자연어 설명 가이드 생성**:
  - **Gemini API**를 활용하여, 수정된 코드가 왜 더 안전한지, 어떤 취약점이 해결되었는지 자연어로 설명하여 개발자의 이해를 돕습니다.

- **직관적인 웹 UI 제공**:
  - React + Vite 기반의 프론트엔드를 통해 사용자가 코드를 쉽게 입력하고, 분석 결과를 시각화된 리포트로 확인할 수 있습니다.

- **AI 자동화 파이프라인**:
  - 코드 파싱(Tree-sitter) -> 취약점 탐지(GraphCodeBERT) -> 코드 변환(CodeT5) -> 결과 설명(Gemini)에 이르는 전 과정을 자동화합니다.

<br/>
<br/>

# 4. Tasks & Responsibilities (작업 및 역할 분담)
|  |  |  |
|-----------------|-----------------|-----------------|
| 김성은 | [프로필 사진] | <ul><li>[담당 역할 1, 예: 프로젝트 총괄]</li><li>[담당 역할 2, 예: Spring Boot 백엔드 아키텍처 설계]</li><li>[담당 역할 3, 예: API 개발]</li></ul> |
| 권태욱 | [프로필 사진] | <ul><li>[담당 역할 1, 예: AI 파이프라인 구축]</li><li>[담당 역할 2, 예: GraphCodeBERT/CodeT5 모델 튜닝]</li></ul> |
| 이정재 | [프로필 사진] | <ul><li>[담당 역할 1, 예: 프론트엔드(React) 개발]</li><li>[담당 역할 2, 예: 분석 결과 시각화 UI/UX 설계]</li></ul> |
| 황지원 | [프로필 사진] | <ul><li>[담당 역할 1, 예: 백엔드 API 개발]</li><li>[담당 역할 2, 예: Gemini API 연동]</li><li>[담당 역할 3, 예: 인프라(EC2) 배포 및 DB(PostgreSQL) 설계]</li></ul> |

<br/>
<br/>

# 5. Technology Stack (기술 스택)
## 5.1 Frontend
| | |
|---|---|
| React | <img src="https://github.com/user-attachments/assets/e3b49dbb-981b-4804-acf9-012c854a2fd2" alt="React" width="100"> |
| Vite | <img src="https://raw.githubusercontent.com/vitejs/vite/main/docs/public/logo.svg" alt="Vite" width="100"> |

*(PDF 기반)*

## 5.2 Backend & Infra
| | |
|---|---|
| Spring Boot | <img src="https://github.com/user-attachments/assets/2e90f331-5f0c-43f1-817d-93d4346808f3" alt="Spring Boot" width="100"> |
| Amazon EC2 | <img src="https://github.com/user-attachments/assets/263f35a0-96b1-4a53-810a-f0f513909c2a" alt="Amazon EC2" width="100"> |
| PostgreSQL | <img src="https://github.com/user-attachments/assets/6b36a87c-c466-4c75-9c59-70ab18d844e3" alt="PostgreSQL" width="100"> |

*(PDF 기반)*

## 5.3 AI
| | |
|---|---|
| PyTorch | <img src="https://github.com/user-attachments/assets/c15b14f8-***-***-***-***" alt="PyTorch" width="100"> |
| TensorFlow | <img src="https://github.com/user-attachments/assets/***-***-***-***-***" alt="TensorFlow" width="100"> |
| **GraphCodeBERT** | (취약점 탐지) |
| **CodeT5** | (코드 변환) |
| **Gemini API** | (자연어 설명) |

*(PDF 기반)*

## 5.4 Cooperation
| | |
|---|---|
| Git | <img src="https://github.com/user-attachments/assets/483abc38-ed4d-487c-b43a-3963b33430e6" alt="git" width="100"> |
| [Notion] | <img src="https://github.com/user-attachments/assets/34141eb9-deca-416a-a83f-ff9543cc2f9a" alt="Notion" width="100"> |

<br/>

# 6. Project Structure (프로젝트 구조)
```plaintext
aegis-ai/
├── frontend-react/        # React + Vite 프론트엔드
│   ├── public/
│   ├── src/
│   │   ├── components/    # 재사용 UI 컴포넌트
│   │   ├── pages/         # 페이지별 컴포넌트
│   │   ├── App.jsx
│   │   └── main.jsx
│   └── package.json
│
├── backend-spring/        # Spring Boot 백엔드
│   ├── src/main/java/com/aegisai/
│   │   ├── controller/    # API 컨트롤러
│   │   ├── service/       # 비즈니스 로직
│   │   ├── model/         # 데이터 모델
│   │   └── repository/    # DB 리포지토리
│   └── build.gradle
│
├── ai-python/             # AI 모델 서빙 (Python/Flask or FastAPI)
│   ├── models/            # GraphCodeBERT, CodeT5 모델 파일
│   ├── app.py             # AI 모델 API 서버
│   └── requirements.txt
│
├── .gitignore
└── README.md              # 프로젝트 문서
