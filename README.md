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

### Comment