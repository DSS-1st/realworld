# RealWorld
***
### 개요  
* 소셜 블로깅 사이트(e.g. Medium.com) Backend 구현 프로젝트
* 테스트 코드를 기반으로 실무자 PR 리뷰를 통한 완성도 제고
* 기본 CRUD, 태그, 팔로우, 좋아요, 토큰 인증 구현   
* 2명으로 2달간 진행(2023.04.11~2023.06.13)
* 오픈 소스 [RealWorld Backend API 사양](https://realworld-docs.netlify.app/docs/specs/backend-specs/endpoints) 준수  

### 담당 영역
* [올찬](https://github.com/a11chan): Article, Tag, Exception, Authentication, [프로젝트 대시보드]((https://www.notion.so/allchan/RealWorld-384b3d9131c64a28a43619ff02de17e3?pvs=4))
* [풀바셋](https://github.com/sdongpil): User, Profile, Comment

### 실무자 PR 리뷰  
* User API
  * [Step-1](https://github.com/DSS-1st/realworld/pull/2), [Step-2](https://github.com/DSS-1st/realworld/pull/3), [Step-3](https://github.com/DSS-1st/realworld/pull/4), [Step-4](https://github.com/DSS-1st/realworld/pull/5), [Step-5](https://github.com/DSS-1st/realworld/pull/30)
* Article API
  * [Step-1](https://github.com/DSS-1st/realworld/pull/7), [Step-2](https://github.com/DSS-1st/realworld/pull/9), [Step-3](https://github.com/DSS-1st/realworld/pull/18)
* Comment API
  * [Step-1](https://github.com/DSS-1st/realworld/pull/11), [Step-2](https://github.com/DSS-1st/realworld/pull/34)

### ERD
  ![ERD_realworld.png](src%2Fmain%2Fresources%2Fdb%2FERD_realworld.png)
  
### 패키지 구조
```
├─main
│  ├─generated
│  ├─java
│  │  └─com
│  │      └─dss
│  │          └─realworld
│  │              ├─article
│  │              │  ├─api
│  │              │  │  └─dto
│  │              │  ├─app
│  │              │  └─domain
│  │              │      └─repository
│  │              ├─comment
│  │              │  ├─api
│  │              │  │  └─dto
│  │              │  ├─app
│  │              │  └─domain
│  │              │      └─repository
│  │              ├─common
│  │              │  ├─aop
│  │              │  ├─auth
│  │              │  ├─config
│  │              │  ├─dto
│  │              │  ├─error
│  │              │  │  └─exception
│  │              │  └─jwt
│  │              ├─tag
│  │              │  ├─api
│  │              │  │  └─dto
│  │              │  ├─app
│  │              │  └─domain
│  │              │      └─repository
│  │              └─user
│  │                  ├─api
│  │                  │  └─dto
│  │                  ├─app
│  │                  └─domain
│  │                      └─repository
│  └─resources
│      ├─db
│      ├─mapper
│      ├─static
│      └─templates
└─test
    ├─java
    │  └─com
    │      └─dss
    │          └─realworld
    │              ├─article
    │              │  ├─api
    │              │  ├─app
    │              │  └─domain
    │              │      └─repository
    │              ├─comment
    │              │  ├─api
    │              │  ├─app
    │              │  └─domain
    │              │      └─repository
    │              ├─common
    │              ├─tag
    │              │  ├─api
    │              │  ├─app
    │              │  └─domain
    │              ├─user
    │              │  ├─api
    │              │  ├─app
    │              │  └─domain
    │              │      └─repository
    │              └─util
    └─resources
```

### 사용 기술
* Java 11
* Spring Boot 2.7.10
* Spring Security 5.7.7 
* Java JWT 4.4.0
* MySQL Server 8.0.29
* MyBatis 3.5.11
* Gradle 7.6.1

### 핵심 로직
* Slug
  * 정의
    * URL에서 웹페이지 내용을 함축하는 단어로 이루어진 구절, 검색 엔진 최적화에 사용됨
    * 프로젝트 내에서는 Article, Comment 관련 API Endpoint에 포함됨(총 8곳)
      * `POST /api/articles/:slug/favorite`
      * `GET /api/articles/:slug/comments`
  * 문제: Slug가 Article(게시글) 내용을 함축하고 유일성을 갖게 해야 함
  * 해결 과정
    * 제목이 게시글 내용을 함축하므로 제목을 기반으로 Slug를 생성하도록 구현
    * 유일성 문제는 게시글이 생성될 때 만들어지는 PK 번호가 접미사로 붙도록 하여 해결
    * 그런데 insert 되기 전에 PK 번호를 알아내어 insert 하는 MySQL 쿼리가 존재하지 않음
    * 이에 Article 테이블의 마지막 PK를 조회 후 Slug 접미사 생성 파라미터에 +1을 추가하도록 구현([전체 코드 링크](https://github.com/DSS-1st/realworld/blob/dfbb3f6fb155670cabe1190b9ce8f8b330b445de/src/main/java/com/dss/realworld/article/app/ArticleServiceImpl.java#L127-L138))
      ```java
      @Override
      @Transactional
      public ArticleResponseDto save(final CreateArticleRequestDto createArticleRequestDto, final Long loginId) {
        Long maxId = articleRepository.findMaxId().orElse(0L);
        Article article = createArticleRequestDto.convert(loginId, maxId + 1);
        articleRepository.persist(article);

        Set<Tag> tags = getTagSet(createArticleRequestDto);
        if (!tags.isEmpty()) saveTags(article, tags);

        return getArticleResponseDto(article, loginId);
      }
      ```
    * Article Update의 경우 Slug 접미사에 Article PK가 그대로 사용되어야 하므로 Slug 생성 시 기존 PK 사용하도록 구현([전체 코드 링크](https://github.com/DSS-1st/realworld/blob/dfbb3f6fb155670cabe1190b9ce8f8b330b445de/src/main/java/com/dss/realworld/article/domain/Article.java#L51-L58C6))
      ```java
      public Article updateArticle(final UpdateArticleRequestDto updateArticleRequestDto) {
        this.slug = Slug.of(updateArticleRequestDto.getTitle(), this.id).getValue();
        this.title = updateArticleRequestDto.getTitle();
        this.description = updateArticleRequestDto.getDescription();
        this.body = updateArticleRequestDto.getBody();

        return this;
      }
      ```
* List Articles
  * 문제
    * 게시글 작성자, 게시글의 Tag, 게시글을 좋아한 사람으로 검색할 수 있어야 함
    * 조회 결과를 페이징할 수 있어야 함(기본값: 1페이지당 게시글 20개)
    * 로그인 여부에 따라 게시글 좋아요, 작성자 팔로우 여부가 다르게 표시되어야 함
  * 해결 과정
    * 검색조건과 연결된 테이블은 총 4가지(article_tag, tag, article_users, users)이며 article을 driving table로 left join 하여 검색 결과에 해당하는 article이 조회되도록 구현
      ```sql
      <select id="list" resultType="Article">
        select a.article_id as id, slug, title, description, body, a.user_id, a.created_at, a.updated_at
        from article a
            left join article_tag at on a.article_id = at.article_id
            left join tag t on at.tag_id = t.tag_id
            left join users u on a.user_id = u.user_id
            left join article_users au on a.article_id = au.article_id
            left join users afu on au.favorited_id = afu.user_id
        <where>
            <if test="tag != null">
                t.name = #{tag}
            </if>
            <if test="author != null">
                u.username = #{author}
            </if>
            <if test="favorited != null">
                afu.username = #{favorited}
            </if>
        </where>
        group by a.article_id
        order by a.created_at desc, a.article_id desc
        limit #{limit} offset #{offset}
      </select>
      ```
    * 위에서 얻은 article 목록을 태그, 팔로우, 좋아요 정보와 결합
      ```java
      private ArticleDtoBinder bindArticleDto(final Long loginId, final Article article) {
        List<String> tagList = articleTagRepository.findTagsByArticleId(article.getId());
        ArticleContentDto content = ArticleContentDto.of(article);
        AuthorDto author = getAuthor(article.getUserId(), loginId);

        boolean favorited = isFavorite(loginId, article);
        int favoritesCount = articleUsersRepository.findCountByArticleId(article.getId());

        return new ArticleDtoBinder(content, author, tagList, favorited, favoritesCount);
      }
      ```
      * `loginId`는 컨트롤러 계층에서 `@AuthenticationPrincipal`을 통해 `SecurityContextHolder`에서 구함
    * 결합한 데이터를 API 응답 형식에 맞게 DTO로 반환
      ```java
      public ArticleListResponseDto list(final String tag, final String author, final String favorited, final Long loginId, int limit, int offset) {
        List<Article> foundArticles = articleRepository.list(tag, author, favorited, limit, offset);
        if (foundArticles.isEmpty()) return new ArticleListResponseDto(0);

        List<ArticleListItemResponseDto> articles = foundArticles.stream()
                .map(article -> getArticleListItemResponseDto(loginId, article))
                .collect(Collectors.toList());

        return new ArticleListResponseDto(articles);
      }
      ```
      * 검색 결과가 없으면 빈 객체를 반환, 있으면 stream을 통해 domain 계층의 데이터를 DTO 객체로 변환

### Git Convention
#### Commit Convention
- feat: 새로운 기능을 추가할 경우
- refactor: 코드 리팩터링
- test: 테스트 추가
- fix: 버그나 잘못된 로직을 고친 경우
- style: 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우
- info: 주석, 정보성 내용 추가/변경

#### Branch Convenction
- GitHub-Flow 전략 사용
- 영문으로 작성
- 공백은 하이픈(-)으로 대체
- 접두사는 Commit 이름 규칙을 따름
- 예시
  - `feature-01-fix-validation-and-test-name`
  - `test-01-add-validation-test-to-domain-layer`
  - `refactor-01-article-json-root-wrapping-method-change`

#### PR Convention
- 이슈 이름_(세부 작업 내용: 생략 가능)
- 기능(feature) 관련 PR은 접두사 생략, 나머지는 Commit 이름 규칙을 따름
- commit 이름을 그대로 가져와 사용 가능
- 예시
  * `1. 회원가입 API 개발_User 도메인 유효성 검사 위치, 테스트 제목 수정`
  * `27. Feed Articles`
  * `refactor-26-apply-value-annotation-to-jwt-signkey`

### 프로젝트 설치 및 사용 방법
* 사전 설치
  * Java 11
  * Docker Desktop: Testcontainers에서 DB 구동을 위해 필요
    * Gradle로 프로젝트 빌드 시 [Testcontainers](https://testcontainers.com/)를 통해 테스트 코드 실행됨
  * MySQL Server 8.0.29
* 프로젝트 구동
  * 프로젝트 파일을 다운받아 압축 해제 후 터미널로 `gradlew build` 실행
  * 테스트 코드가 실행된 후 빌드가 완료되면 다음 경로에서 테스트 결과 확인 가능
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

### 노션 저장소 링크
* [참고 자료](https://www.notion.so/allchan/aae94d2d1e0c469ca51a127c0a026d55?v=2cd05d89df60400d936a6b6701e1d633&pvs=4)
* [트러블 슈팅](https://www.notion.so/allchan/74c8db21df554b30b07b70793a424dc9?v=fef94141df5144af94e6e8cae923e8bf&pvs=4)