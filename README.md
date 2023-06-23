# RealWorld

### 개요  
* 소셜 블로깅 사이트(e.g. Medium.com) Backend API 구현 프로젝트
* 기본 CRUD, 태그, 팔로우, 좋아요, 토큰 인증 구현  
* 테스트 코드를 기반으로 실무자 PR 리뷰를 통한 완성도 제고  
* 2명으로 2달간 진행(2023.04.11~2023.06.13)
* 오픈 소스 [RealWorld Backend API 사양](https://realworld-docs.netlify.app/docs/specs/backend-specs/endpoints) 준수  

### 담당 영역
* [올찬](https://github.com/a11chan): DB 모델링, Article, Tag, 예외 처리, 인증
* [풀바셋](https://github.com/sdongpil): User, Profile, Comment

### 실무자 4명의 코드 리뷰 적용  
* User API
  * [Step-1](https://github.com/DSS-1st/realworld/pull/2), [Step-2](https://github.com/DSS-1st/realworld/pull/3), [Step-3](https://github.com/DSS-1st/realworld/pull/4), [Step-4](https://github.com/DSS-1st/realworld/pull/5), [Step-5](https://github.com/DSS-1st/realworld/pull/30)
* Article API
  * [Step-1](https://github.com/DSS-1st/realworld/pull/7), [Step-2](https://github.com/DSS-1st/realworld/pull/9), [Step-3](https://github.com/DSS-1st/realworld/pull/18)
* Comment API
  * [⭐Step-1](https://github.com/DSS-1st/realworld/pull/11), [Step-2](https://github.com/DSS-1st/realworld/pull/34)

### ERD
  ![ERD_realworld.png](src%2Fmain%2Fresources%2Fdb%2FERD_realworld.png)

### 핵심 로직

<details>
<summary>Slug</summary>
<div markdown="1">

* Slug 정의
  * URL에서 웹페이지 내용을 함축하는 단어로 이루어진 구절, 검색 엔진 최적화에 사용됨
  * 프로젝트 내 Article, Comment 관련 API Endpoint에 포함(총 8곳)
    * `POST /api/articles/:slug/favorite`
    * `GET /api/articles/:slug/comments`
* 요구사항
  * Slug가 Article(게시글) 내용을 함축하고 유일성을 갖게 해야 함
* 구현과정
  * 게시글 내용을 함축하는 제목을 기반으로 Slug를 생성하도록 구현
  * 유일성 문제는 게시글이 생성될 때 만들어지는 PK 번호가 접미사로 붙도록 하여 해결
  * 그런데 insert 되기 전에 PK 번호를 알아내어 insert 하는 MySQL 쿼리가 존재하지 않음
  * 이에 Article 테이블의 마지막 PK를 조회 후 Slug 접미사 생성 파라미터에 +1을 추가하도록 구현([해당 코드](https://github.com/DSS-1st/realworld/blob/dfbb3f6fb155670cabe1190b9ce8f8b330b445de/src/main/java/com/dss/realworld/article/app/ArticleServiceImpl.java#L127-L138))
  * Article Update의 경우 Slug 접미사에 Article PK가 그대로 사용되어야 하므로 Slug 생성 시 기존 PK 사용하도록 구현([해당 코드](https://github.com/DSS-1st/realworld/blob/dfbb3f6fb155670cabe1190b9ce8f8b330b445de/src/main/java/com/dss/realworld/article/domain/Article.java#L51-L58C6))
  * 향후 [Slugify](https://github.com/slugify/slugify) 라이브러리를 적용하여 완성도 향상
  
</div>
</details>

<details>
<summary>List Articles</summary>
<div markdown="1">

* 요구사항
  * 게시글 작성자, Tag, 게시글을 좋아한 사람으로 검색할 수 있어야 함
  * 조회 결과를 페이징할 수 있어야 함(기본값: 1페이지당 게시글 20개)
  * 로그인 여부에 따라 게시글 좋아요, 작성자 팔로우 여부가 다르게 표시되어야 함
* 구현과정
  * 검색조건과 연결된 테이블은 총 4가지(article_tag, tag, article_users, users)이며 article을 driving table로 left join 하여 검색 결과에 해당하는 article이 조회되도록 구현([해당 코드](https://github.com/DSS-1st/realworld/blob/53c69ea03482d9d39b54ddc4e1aa78376114a354/src/main/resources/mapper/ArticleRepository.xml#L53-L75))
  * 위에서 얻은 article 목록을 태그, 팔로우, 좋아요 정보와 결합([해당 코드](https://github.com/DSS-1st/realworld/blob/53c69ea03482d9d39b54ddc4e1aa78376114a354/src/main/java/com/dss/realworld/article/app/ArticleServiceImpl.java#L56-L65))
  * 결합한 데이터를 API 응답 형식에 맞게 DTO로 반환([해당 코드](https://github.com/DSS-1st/realworld/blob/53c69ea03482d9d39b54ddc4e1aa78376114a354/src/main/java/com/dss/realworld/article/app/ArticleServiceImpl.java#L41-L50))
  * 검색 결과가 없으면 빈 객체를 반환, 있으면 stream을 통해 domain 계층의 데이터를 DTO 객체로 변환

</div>
</details>

### 사용 기술
* Java 11
* Spring Boot 2.7.10
* JUnit 5
* Spring Security 5.7.7 
* Java JWT 4.4.0
* MySQL Server 8.0.29
* MyBatis 3.5.11
* Gradle 7.6.1

### 프로젝트 설치 및 사용 방법

<details>
<summary>사전 설치</summary>
<div markdown="1">

* Java 11
* Docker Desktop: Testcontainers에서 DB 구동을 위해 필요
  * Gradle로 프로젝트 빌드 시 [Testcontainers](https://testcontainers.com/)를 통해 테스트 코드 실행됨
* MySQL Server 8.0.29
  
</div>
</details>

<details>
<summary>프로젝트 구동</summary>
<div markdown="1">

* 프로젝트 파일을 다운받아 압축 해제 후 터미널로 `gradlew build` 실행
* 빌드가 완료되면 다음 경로에서 테스트 결과 확인 가능
    * `프로젝트 루트 폴더/build/reports/tests/test/index.html`
* MySQL DB 초기화
  * Workbench를 통해 루트 계정으로 `realworld` schema 생성 필요
  * `application-dev.yml`에서 username, password 알맞게 수정
    ```yml
    spring:
      datasource:
        driverClassName: net.sf.log4jdbc.sql.jdbcapi.DriverSpy
        url: jdbc:log4jdbc:mysql://localhost:3306/realworld
        username: ********
        password: ********
    ```
  * 앱 구동 전 `src/main/resources/db/V1_create.sql` 파일로 테이블 생성 필요
* `프로젝트 루트 폴더/build/libs`로 이동 후 터미널로 `java -jar realworld-0.0.1-SNAPSHOT.jar` 실행
* `http://localhost:8080/` 을 호스트 주소로 하여 [API Endpoints](https://realworld-docs.netlify.app/docs/specs/backend-specs/endpoints) 사용 가능
* 실행 후 터미널에서 `Ctrl + C` 입력하여 종료 가능
  
</div>
</details>

### 참고 문서
* 프로젝트 회고: https://bit.ly/3Jodl1I
* 프로젝트 대시보드: https://bit.ly/3JkAzpr
  * 작업현황, 개발수칙, 트러블 슈팅, 참고 자료, 협업 내용 정리
