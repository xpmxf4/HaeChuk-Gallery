<h1 align="center">
  <br>
<img src="https://image.fmkorea.com/files/attach/new2/20220531/486616/51839/4676868775/5d8562fb2170d0d7556909e9e838ec02.jpg" alt="해외축구갤러리" width="600">
  <br>
  해외축구갤러리
  <br>
</h1>

<h4 align="center">대용량 데이터 처리와 비용 효율적인 인프라 구축을 위한 Spring Boot 기반 프로젝트</h4>

<p align="center">
  <a href="#🏗️ Server Archi.">🏗️ Server Archi.</a> •
  <a href="#🔑 Key Features">🔑 Key Features</a> •
  <a href="#👿 Trouble Shooting & Problem Solving">👿 Trouble Shooting & Problem Solving</a> •
  <a href="#🗂️ Folder Structure">🗂️ Folder Structure</a> •
  <a href="#💽 ERD">💽 ERD</a>
</p>




## 🏗️ Server Archi.

<img src="/Users/xpmxf4/Downloads/HaeChukGael.jpg" alt="HaeChukGael" style="zoom:80%;" />

## 🔑 Key Features

- **대용량 데이터 처리**: 200만 건에서 1억 건 이상의 데이터 처리 능력 향상.

- **비용 절감**: RDS 대신 MySQL Group Replication 사용으로 매월 68,000원 절감.

- **데이터 복구 및 백업 실패 대응**: 로컬 테스트 중 발생한 데이터 손실 문제 해결.

- **CI/CD**: GitHub Actions을 이용한 0원의 비용으로 CI/CD 구축.

- **성능 개선**: 스케줄러와 통계 테이블을 통해 성능 75% 개선.

- **검색 성능 향상**: 풀텍스트와 B-Tree 인덱스 비교 후 사용하여 검색 시간을 대폭 단축.



## 🗂️ Folder Structure

  ```bash
  .
  ├── main
  │   ├── java
  │   │   └── hailyounghan  # 도메인 기준으로 폴더 구분
  │   │       ├── BoardApplication.java
  │   │       ├── comment
  │   │       ├── common
  │   │       ├── member
  │   │       ├── popularPost
  │   │       └── post
  │   └── resources
  │       ├── application.yml
  │       ├── static
  │       └── templates
  └── test
      ├── java
      │   └── hailyounghan # 도메인 기준으로 폴더 구분
      │       ├── comment
      │       ├── member
      │       ├── popularPost
      │       └── post
      └── resources
  ```