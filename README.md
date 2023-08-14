# 🔥 hotsix-project
칸반보드 기반 협업툴 서비스 만들기 프로젝트

****

## 프로젝트 결과물

#### 배포된 주소 링크
http://ec2-3-38-181-21.ap-northeast-2.compute.amazonaws.com:8080/

#### 시연영상 링크
https://youtu.be/XYAlvqdgweY

****

## 개발 기간
2023.08.07. ~ 2023.08.14.

****

## 기술 스택
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"><img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"><img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<br>

<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"><img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">

<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"><img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"><img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">

<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"><img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white"><img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
<img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"><img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

<img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"><img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"><img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"><img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">

****

## 역할 분담 -> 자세한 내용 개발 프로세스 참고
* 서예린 - 사용자 기능 / 소셜로그인, 키워드 검색, 프론트
* 조우진 - 보드 관리 기능 / 프론트
* 최정은 - 컬럼 관리 기능 / 테스트 코드 및 더미 데이터, Swagger 적용
* 최혁진 - 카드 관리 및 추가 기능 / CI/CD 파이프라인 구축 및 배포, 프로필 이미지, 알림 기능
* 조해나 - 카드 상세 및 추가 기능 / 프론트

## 개발 프로세스
<details>
  <summary>
    자세히 보기
  </summary>
<br>
  
- [x]  프로젝트 스코프 정하기
- [x]  Trello 직접 사용해보기
- [x]  API 명세 작성하기
- [x]  ERD 작성하기
- [x]  와이어프레임 작성하기
- [x]  본격적인 백엔드 개발하기
    - 구현해야 하는 기능들
      <details>
        
        ### 트렐로의 필수 기능
        
        - **사용자 관리 기능** - `서예린`
            - [x]  로그인 / 회원가입 기능
            - [x]  사용자 정보 수정 및 삭제 기능
         
        - **보드 관리 기능** - `조우진`
            - [x]  보드 생성
            - [x]  보드 수정 (이름, 배경 색상, 설명)
            - [x]  보드 삭제 (생성한 사용자만)
            - [x]  보드 초대 (특정 사용자 초대해 협업)
      
        - **컬럼 관리 기능** - `최정은`
            - [x]  컬럼 생성 (보드 내부 컬럼 생성)
            - [x]  컬럼 이름 수정
            - [x]  컬럼 삭제
            - [x]  컬럼 순서 이동
   
        - **카드 관리 기능** - `최혁진`
            - [x]  카드 생성 (컬럼 내부 카드 생성)
            - [x]  카드 수정 (이름, 설명, 색상, 작업자 할당 및 변경)
            - [x]  카드 삭제
            - [x]  카드 이동 (같은 컬럼 내 위치 변경, 다른 컬럼 이동)

        - **카드 상세 기능** - `조해나`
            - [x]  댓글 달기
            - [x]  날짜 지정 (마감일 설정 및 관리)
        
        ### 스페셜 코스
        
        - **테스트 코드 도입하기** - `최정은`
            - [x]  테스트 코드 도입하기

        - **CI/CD 파이프라인 구축하기** - `최혁진`
            - [x]  CI/CD 파이프라인 구축하기 => Github Actions
                    
        - **더미 데이터 활용해보기** - `최정은`
            - [x]  더미 데이터 활용해보기
           
        - **카드에 고급 기능 구현하기**
            - [x]  카드 파일 첨부 / 다운로드 - `최혁진`
            - [x]  카드 내에 체크리스트 추가 - `조해나`

        - **알림 기능 구현하기** - `최혁진`
            - [x]  카드 상태 변동 시 로그(콘솔)로 알림 받기
        
        ### 그 외 추가 기능
        - [x]  소셜 로그인 - `서예린`
        - [x]  카드 라벨 기능 - `조해나`, `최혁진`
        - [x]  키워드로 카드 검색 - `서예린`
        - [x]  프로필에 이미지 넣기 - `최혁진`
        - [x]  Swagger 적용
        - [ ]  DB 최적화

        </details>
       
- [x]  테스트 및 버그 수정하기
- [x]  AWS 클라우드에 배포하기
- [x]  프론트엔드 개발도 해보기

         - [x]  회원가입, 로그인, 프로필 페이지 구성 및 연결 - `서예린`
         - [x]  메인, 보드 페이지 구성 및 연결 - `조우진`
         - [x]  전반적인 CSS, 카드 상세 페이지 구성 및 연결 - `조해나`
       
</details>

****
## 설계

### 최종 설계

<details>

##### API 명세서
https://documenter.getpostman.com/view/27924273/2s9Xy5MqxZ

##### ERD
![hotsix-erd](https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/1c4e59a0-dd5e-4047-b7a6-30ecb5b42e1d)


##### 최종 화면 구성
<img width="45%" src="https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/b12e740d-aef6-4e51-a89d-19a38186afcd">
<img width="45%" src="https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/28638728-3c2d-4001-a59d-848fba89013d">
<img width="45%" src="https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/14e80a42-5db5-4c1c-99de-8486605d5914">
<img width="45%" alt="image" src="https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/f91e891b-4532-49ff-a5ff-31be39c012ce">
<img width="45%" alt="image" src="https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/0422e6b2-944e-49f2-a802-eb13cec1a1b9">
<img width="45%" alt="image" src="https://github.com/spring-b-hotsix/hotsix-project/assets/131599243/fe96f49d-bb37-49a6-9066-15597ee52463">

</details>

### 초기 설계

<details>

##### S.A (API 명세, Github Rule 포함)
https://www.notion.so/6-S-A-1a2e6e1486dc4ae6831d584d1f521ae2

##### ERD
https://lucid.app/lucidchart/e1c3dd6b-19cb-4a13-8d87-b0cbc1e59c64/edit?invitationId=inv_f3f01798-d101-4abf-8f3b-7c6aa0d4fab0&page=0_0#

##### 와이어프레임
https://www.figma.com/file/jXoDCYNfmOAzwcsDciFJJP/hotsix?type=design&node-id=0-1&mode=design&t=u68VeAxYqHSwuAMR-0

</details>
