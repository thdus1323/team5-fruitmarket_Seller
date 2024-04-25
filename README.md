# 1단계 : 상품 판매자 서버

1. 들어가기 전, 간단한 정리

이름 : gwaill nara / 과일나라

중요 흐름

요청 >> controller >> Service >> Repository >> DB

작업순서

DB >> **Repository >> Service >> Controller >>** 고객

: 작업 순서는 Controller부터 가는 것이 있고 Repository에서 가는 것이 있는데

이번에는 바로 위의 순서를 따랐다.

⭐ 중요 특징

1) Repository : 쿼리

💫중요 쿼리(”” 편의상 생략)

- **Update** 해당_tb **set** 인수=?, … **where id**=?
- **Insert into** 해당_tb(인수들, created_at) **values**(?,?…,now())
- **delete from** 해당_tb **where id**=?
- **select * from** 해당_tb **order by id desc**

2) Service : 연결, 서비스,

-transitional(**안으로 보면 2가지 정도**의 단계이나 밖에서 봤을 때 **하나의 일 ⇒ 그래서 잠깐 멈춰,일을 동기적으로? 순차적으로 해결**)

3) Controller 요청에 대한 뷰 반환 및 길잡이

-Post : 자료 입력-v

-get : 자료 획득-v

## 1단계 순서 및 중요 내용 정리

1. 프로젝트 생성 및 환경설정

-야물 및 테이블 생성

-workbench에 데이터베이스 생성

1. view 생성 및 view 연결
    
    : 뷰/페이지에 따른 순서보다 기능별 생성에 따른 순서를 따르는 것이 디버깅하기 쉽다.
    
    1) 메인페이지 : 상품목록보기
  ![상품목록](https://prod-files-secure.s3.us-west-2.amazonaws.com/fadaf8f9-4b09-4f3d-9e72-038ace21b86b/8d9baaab-b95c-4fe2-aef3-cf7cd7d8845d/Untitled.png)

![Untitled](https://github.com/thdus1323/team5-fruitmarket01/assets/153582422/00a80cd4-e528-4e36-a9a7-56dcd6d69be8)






