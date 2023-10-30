📌 Board Project by HailYoungHan
Spring Boot 기반의 게시판 웹 애플리케이션입니다.

## 📝 목차
- [프로젝트 구조](#프로젝트-구조)
- [기능 목록](#기능-목록)
- [개발 환경](#개발-환경)
- [실행 방법](#실행-방법)
- [연락처](#연락처)

## 🏛 프로젝트 구조
```bash
.
├── BoardApplication.java      - 애플리케이션 실행점
├── controller                 - 웹 요청을 처리하는 컨트롤러들
├── dto                        - 데이터 전송 객체 (Data Transfer Objects)
├── entity                     - JPA 엔티티 클래스
├── exception                  - 사용자 정의 예외 및 처리 핸들러
├── repository                 - JPA 레포지토리 및 사용자 정의 레포지토리
├── service                    - 비즈니스 로직 처리 서비스 클래스
├── util                       - 공통 유틸리티 (ex. 비밀번호 암호화)
└── validation                 - 사용자 정의 검증 어노테이션 및 그 구현체
```


## 🔍 기능 목록
- 게시글 작성, 조회, 수정, 삭제
- 댓글 작성, 조회, 수정, 삭제
- 회원 가입, 수정, 조회, 탈퇴

## 🛠 개발 환경
### 기본 환경

Java 버전: Java 11 <br>
Spring Boot 버전: 2.7.15 <br>
Build Tool: Gradle <br>
### 주요 라이브러리 및 의존성
JPA: spring-boot-starter-data-jpa를 통해 JPA 기능을 사용합니다 <br>
Web: spring-boot-starter-web를 이용해 웹 기능을 구현합니다 <br>
Validation: spring-boot-starter-validation을 사용하여 입력 검증 기능을 제공합니다 <br>
QueryDSL: 버전 5.0.0을 사용하여 타입 안전한 쿼리를 지원합니다 <br>
p6spy: SQL 로깅 및 물음표 파라미터를 볼 수 있는 기능을 제공합니다 <br>
Lombok: 반복적인 코드를 줄이기 위해 사용되는 라이브러리입니다 <br>
H2 Database: 개발용 인메모리 데이터베이스입니다 <br>
JUnit Platform: spring-boot-starter-test와 함께 테스트 작성 및 실행을 위한 환경을 제공합니다 <br>

## 🚀 실행 방법
1. Repository를 Clone 합니다.
```bash
git clone https://github.com/xpmxf4/MonsterBoard.git
```

2. Gradlew를 이용해 프로젝트를 실행합니다.
```bash
./gradlew bootRun 
```

3. 브라우저에서 http://localhost:8080 (또는 설정한 포트)로 접속하여 애플리케이션을 사용합니다.

## 💌 연락처
Email   : b2st.engineer@gmail.com<br>
Github  : https://github.com/xpmxf4 <br>
Blog    : https://xpmxf4.tistory.com