
# Client Credentials Grant Type 방식
![](https://velog.velcdn.com/images/blacklandbird/post/7e286b79-342b-4829-a87f-e792f1ede82a/image.png)

### Filters
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdG7K7O%2Fbtq3YLd75UT%2FDggosK4oiDRpxYclymDb90%2Fimg.png)

1. 인증 시, 새로운 Security Context(인메모리 세션저장소)를 생성하여 SecurityContextHolder에 저장.

   a. usernamePasswordAuthenticationFilter) 인증 성공후 SecurityContext에 `UsernameAuthentication` + `Authentication` 저장.
   b. 인증 완료 후, Session에 SecurityContext 저장.

2. 권한 시, Session에서 SecurityContext를 꺼냄 -> SecurityContextHolder에 저장.

    a. SecurityContext에서 `Authentication` 있으면, 인증된 유저.

- `Authentication Filter` : 사용자의 요청을 받아 인증에 사용되는 UsernamePasswordAuthenticationToken을 생성해주는 필터

- `UsernamePasswordAuthenticationFilter` : 인증되지 않은 사용자 리다이렉트 필터.
```
// 5. form 로그인 해제 (UsernamePasswordAuthenticationFilter 비활성화)
http.formLogin().disable();
```
- `BasicAuthenticationFilter` : 헤더 `Basic` 인증 처리 -> SecurityContextHolder에 저장.
```
// 6. username, password 헤더 로그인 방식 해제 (BasicAuthenticationFilter 비활성화)
http.httpBasic().disable();
```

- `OncePerRequestFilter` : filter을 여러번 등록함에 따라, 여러번 호출되는 이슈를 위해 요청당 한번만 실행을 보장.


![](https://velog.velcdn.com/images/mooh2jj/post/2aaad7fc-409b-4862-8577-7ee283dba929/image.png)

- Authentication

- Principal
- GrantAuthority

## 2.7 -> 3.x
### AuthenticationManager

2.7.x 버전부터 선언방식이 변경되면서 AuthenticationManager를 다른 방식으로 설정을 해줬어야 했습니다.
```

```

## Trouble
- authenticationManager must be specified

### 그 외 궁금한거.
`AuthenticationEntryPoint` : JWT 필터에서 예외가 발생하여, DispatcherServlet에 도달하지 못하기에 별도 처리를 위한 Handler



## 이론

### Session
[Browser] -> [Server] // create session.
[Browser] <-          // browser save session in cookie.


### Access Token
[Browser] -> /login -> [server] // sessionId exists? Access Token exists?
[Browser] <-                    // create Access Token (in `JwtAuthenticationFilter` )
[Browser] ->                    // 


### cors, x-frame, 

- 목적 : "인증에 초점(너 내가 인증 준 애가 맞구나!)"
        "암호가 아니다!"
- 방법 :
   1. Http Basic : `https://` 형태로 "email","password"가 암호화 되어 form 제출.
   2. Bear 인증 : Json Web Token 형태 이므로, "email","password"가 탈취되는 것이 아니라 Token이 탈취됨. (Token은 일정기간 후 재생성.)
         
-
### Filters

[UsernamePasswordAuthenticationFilter] - Jwt인증Filter
      1. 로그인 시도.
      2. PrincipalDetailService@loadUserByUsername() 실행 -> 유저 DB 확인
      3. PrincipalDetails을 세션에 저장.
      4. JWT 토큰 성성
[BasicAuthenticationFilter] - Jwt권한Filter
      1. 



### 궁금한거
1. /login 은 어떻게 가능하지?
```
securityConfig.java

addFilter(UsernamePasswordAuthenticationFilter.class) 해주면, /login 등록하여 사용하는 필터가 등록되어 사용 가능.

```


 TODO Template 
1.  Dummy data 넣는 방법
    1. data.sql 스크립트 파일 (src/main/resource) 에 넣어두기
    2. @PostConstruct
    3. @ApplicationRunner
2.  구글/네이버 로그인 넣기
3.  세션 만들기
4.  데이터 합치는 방법