# schedule-jpa
# 프로젝트 개요
- 일정, 댓글, 유저 저장, 조회, 수정, 삭제 기능 
- 일정 페이징 조회 
- JPA Auditing을 활용한 작성일, 수정일 필드 
- 지연로딩, 영속성 전이, 고아 객체 제거 
- Validation 활용한 다양한 예외처리 
- JWT를 활용한 회원가입, 로그인(Security 사용없이)
- PasswordEncoder로 비밀번호 암호화 
- JWT 토큰 발급 후 cookie에 저장, header에 추가 
- Filter 적용 
- 다양한 상태코드 반환(200, 201, 400, 401, 403, 500 등)
- GlobalExceptionHandler 활용한 예외처리 
- 유저에 권한을 추가하여 인가 처리 - 일정을 ROLE_ADMIN의 권한을 가진 사람만 수정, 삭제 가능
- 외부 API(날씨 정보 데이터)를 활용하여 오늘의 날씨 조회( RestTemplate / WebClient)

# 사용 기술 스택
# API 명세서
- Schedule
- ![image](https://github.com/user-attachments/assets/516c89be-438f-4be2-82ab-d5f44aa4f865)
- User 
- 개별조회, 수정, 삭제 시 해당 user정보의 주인이여야 함.
- ![image](https://github.com/user-attachments/assets/fe5efa21-f689-4087-a08f-f3d4165b239b)
- Comment
- ![image](https://github.com/user-attachments/assets/a116fe20-7e87-4423-b5c8-dd7a32310bee)
# ERD
![image](https://github.com/user-attachments/assets/1b17e53f-8e5e-4299-a00a-f3de0be80c84)
# API TEST
### User
- 회원가입(join)
  ![image](https://github.com/user-attachments/assets/8a669816-2e18-4b43-ad16-23d965c85454)
- 로그인(login)
  ![image](https://github.com/user-attachments/assets/f05a17af-dbe1-4f67-9561-798da8895635)
- 회원 개별 조회(토큰 확인)
  ![image](https://github.com/user-attachments/assets/5934f529-c665-41f0-bf7a-0d8544bc0c4d)
- 회원 수정(토큰 확인)
  ![image](https://github.com/user-attachments/assets/d54d854f-3500-460a-a586-7379e7d9effc)
- 회원 탈퇴(토큰 확인)
  ![image](https://github.com/user-attachments/assets/602edcde-3323-445e-9e93-eae92b18426e)
### Schedule
조건
- [ ]  유저에 `권한`을 추가합니다.
- [ ]  권한은 `관리자`, `일반 사용자` 두 가지가 존재합니다.
- [ ]  JWT를 발급할 때 유저의 권한 정보를 함께 넣어줍니다.
- [ ]  일정 수정 및 삭제는 `관리자` 권한이 있는 유저만 할 수 있습니다.
따라서 일정을 생성한 creator는 수정, 삭제가 불가하고 관리자만 수정, 삭제할 수 있도록 설정했음.

- 일정 등록
  ![image](https://github.com/user-attachments/assets/daa8af62-5f02-4694-b870-120ec3a5d61c)
- 일정 전체 조회
  ![image](https://github.com/user-attachments/assets/a475202d-6f55-4004-9d34-ee232b6f09ca)
- 일정 개별 조회
  ![image](https://github.com/user-attachments/assets/c487f169-439e-4a47-9ef4-bdb1eb1b56f3)
- 일정 수정 성공(ROLE_ADMIN 일때만 수정 가능)
  ![image](https://github.com/user-attachments/assets/c7e0776f-1177-467a-9cd3-b3d5cb5d7ed6)
- 일정 수정 실패 예시
  ![image](https://github.com/user-attachments/assets/f5505d9e-3e98-4ddd-bca6-46c711bf671d)
- 일정 삭제(ROLE_ADMIN 일때만 삭제 가능)
  ![image](https://github.com/user-attachments/assets/df28873d-96bc-4a95-a156-39f04436e01c)
- 일정 삭제 실패 예시
  ![image](https://github.com/user-attachments/assets/8b354e2d-423e-43ee-b2df-0da15fca4c46)
### Comment