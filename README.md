
# Client Credentials Grant Type 방식
![](https://velog.velcdn.com/images/blacklandbird/post/7e286b79-342b-4829-a87f-e792f1ede82a/image.png)

### Filters
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdG7K7O%2Fbtq3YLd75UT%2FDggosK4oiDRpxYclymDb90%2Fimg.png)

1. 인증 시, 새로운 Security Context를 생성하여 SecurityContextHolder에 저장.

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


## 2.7 -> 3.x
### AuthenticationManager

2.7.x 버전부터 선언방식이 변경되면서 AuthenticationManager를 다른 방식으로 설정을 해줬어야 했습니다.
```


```

## Trouble

- authenticationManager must be specified



### 그 외 
`AuthenticationEntryPoint` : JWT 필터에서 예외가 발생하여, DispatcherServlet에 도달하지 못하기에 별도 처리를 위한 Handler
