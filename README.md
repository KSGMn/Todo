# Todo-List
<br><br>

## 설명
http://localhost:8080/swagger-ui/index.html에서 작성된 api들을 확인할 수 있습니다.<br>
docker-compose파일과 application.yml파일에서 추가설정 후 실행 가능합니다.
<br><br>

## Todo
간단한 Todo-List입니다.<br>
하루마다 반복할 수 있는 일정과 일반 일정을 저장하고 관리할 수 있습니다.
<br><br>

## 주요 기능
기본적인 CRUD 기능: 할 일 생성, 조회, 수정, 삭제.<br>
하루 반복 일정: 스케쥴러와 SQL 쿼리문을 활용하여 매일 자정 12시에 완료된 일정을 미완료 상태로 업데이트.<br>
JWT 토큰 인증: 사용자 인증 및 권한 관리.<br>
Docker 및 Docker-Compose: 애플리케이션 컨테이너화를 통해 개발의 편리성 향상.<br>
회원가입: Java MailSender를 통해 인증번호 확인 후 회원가입.
<br><br>

### 회원가입 기능
유저 아이디와 닉네임 중복확인 후 이메일로 인증번호 전송.<br>
인증번호 입력 후 전송된 인증번호와 일치하는지 확인.<br>
비밀번호 패턴에 맞게 비밀번호 입력 후 회원가입.
<br><br>

### 로그인 기능
데이터베이스에 저장된 유저 아이디와 비밀번호가 일치하는지 확인 후 로그인.<br>
로그인과 동시에 Jwt토큰을 이용하여 accessToken과 refreshToken을 생성.
<br><br>

### 사용자 인증 및 관리
Spring Security를 이용하여 보안 활성화.<br>
accessToken은 쿠키로 저장하여 클라이언트로 전송, refreshToken은 redis에 저장.<br>
accessToken이 만료되면 redis에 있는 refreshToken이 유효한 토큰인지 확인 후 accessToken재발급.<br>
회원가입과 로그인을 제외한 모든 api요청과 경로는 인증되지 않은 사용자는 접근 불가.
<br><br>

### 로그아웃
로그아웃과 동시에 클라이언트에 있는 쿠키를 삭제하고 accessToken과 refreshToken을 redis에 블랙리스트로 저장하여 재사용 방지.
<br><br>

### CRUD 기능
RestApi와 JAP를 이용하여 간편하고 가독성 높은 코드 구현.<br>
로그인한 유저의 이름을 조회하여 해당 유저가 작성한 Todo만 조회 가능.
<br><br>

### 에러 처리
CustomException을 생성하여 상황에 맞게 상태코드와 메세지 설정.
<br><br>

## 사용 기술 및 환경
Backend: Spring Boot, JPA, MySQL, Java 17<br>
Frontend: React<br>
Authentication: JWT (JSON Web Token)<br>
Infra: Docker, Docker-Compose<br>
Scheduler: Spring 스케줄러 및 SQL 쿼리문 활용<br>
Languege: Java, JavaScript<br>
<br><br>

## 성능 테스트
Postman을 활용하여 테스트하였습니다.<br>
CRUD에 대한 테스트코드를 작성하여 테스트하였습니다.
<br><br>

## 기타
개발 편의성을 위해 nomdemon을 사용하였습니다.
<br><br>










