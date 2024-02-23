# 이론

## Session
| Realation             | Description | 
|-----------------------|-------------|
| [Browser] -> [Server] | 세션 생성       |
| [Browser] <-          | 쿠키에 저장      |


## Access Token
| Relation | Description                        |
| -- |------------------------------------|
| [Browser] -> /login -> [server] | 세션ID 있나? 액세스 토큰 있나?                | 
| [Browser] <- | 액세스 토큰 생성(JwtAuthenticationFilter) | 
| [Browser] -> |                                    |

- 목적 : "인증이 목적임(암호가 아님) (너 내가 인증 준 애가 맞구나!)"
- 방법 :
    1. Http Basic : `https://` 형태로 "email","password"가 암호화 되어 form 제출.
    2. Bear 인증 : Json Web Token 형태 이므로, "email","password"가 탈취되는 것이 아니라 Token이 탈취됨. (Token은 일정기간 후 재생성.)

## SSL
- 통신(네트워크) 암호 제공 역할
- Transport 레이어(물데네,**트**세프응)의 보안 프로토콜
- https = http + ssl

## x-frame-option
1.  "<'frame>", '<'iframe>', '<'object>', '<'embed>', '<'applet>' 태그내에서 실행 되는 것
2.   deny (거부), sameorigin (동일 사이트는 허용), allow-from (지정된 Uri 허용)
3.   클릭재킹 공격이 발생가능하므로, sameorigin 으로 보통한다.

## Basic 인증 & Bearer 인증
- HTTP 인증방식은 2가지
  1. Basic
     1.  base64 인코딩
     2. `Authorization: Basic base64({USERNAME}:{PASSWORD})` 형태
  2. Bearer(소유자)
     1. oauth2.0 토큰 인증

----

# Oauth2.0 인증방식
## Authorization Code
   1. 권한 코드(일회성 사용)를 사용, 보안과 권한을 분리하기 위해.
   2. 4가지 타입 중 가장 복잡한 방식.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbo2Pk2%2FbtqudUcxHtg%2FfJCQKYtKnTsa97iwfq1zK1%2Fimg.png)
ex)
1. 사용자가 구글에 인증 요청.
2. 구글 로그인 화면 표시 -> 애플리케이션 요청 승인 -> 구글 권한 코드 생성
3. 구글이 애플리케이션에 권한 코드 반환 -> 사용자에게 반환.
4. 사용자가 권한 코드를 사용하여 구글 액세스 토큰 요청
5. 구글이 유효한 권한 코드를 받아, 클라이언트에게 액세스 토큰 제공
6. 액세스 토큰을 사용하여, Google API와 같은 어플리케이션에 접근

| Relation                                                      | Description                     |
|---------------------------------------------------------------|---------------------------------|
| [Client] ->  [Server] -> [Google]                             | 권한코드 요청                         | 
| (POPUP=Resource Owner) "'Server' 허락하시면 Yes 누르세요"              | 허락함(권한코드 발급)                    |
| .............[Server] <- [Google]                             | /oauth/authorize                |
| [Client] <- [Server]                                          | redirect_url?code=xxxxx         | 
| [Client]............. -> [Google]                             | 액세스 토큰 요청 (code + redirect_url) |
| [Client]............. -> [Google Oauth Provider]              | 액세스 토큰 검증                       |
| [Client]............. -> [Google Oauth Provider] -> [Google 계정] | Resource에 접근                    |
| [Client]                                         | Google 계정으로 로그인                 |
| (POPUP)  | 끝                               |


## Implicit "모바일에서 받아서, 서버에 등록하는 반대 방향은 안될까?"
   1. 자바스크립트/모바일 같은 클라이언트들을 지원하기 위한 승인 유형
   2. 직접 액세스 토큰 획득 ( redirect_url : 자바스크립트 or 모바일 )

```
// https://developers.google.com/identity/protocols/oauth2/javascript-implicit-flow
function oauthSignIn() {
  // Google's OAuth 2.0 endpoint for requesting an access token
  var oauth2Endpoint = 'https://accounts.google.com/o/oauth2/v2/auth';

  // Create <form> element to submit parameters to OAuth 2.0 endpoint.
  var form = document.createElement('form');
  form.setAttribute('method', 'GET'); // Send as a GET request.
  form.setAttribute('action', oauth2Endpoint);

  // Parameters to pass to OAuth 2.0 endpoint.
  var params = {'client_id': 'YOUR_CLIENT_ID',
                'redirect_uri': 'YOUR_REDIRECT_URI',
                'response_type': 'token',
                'scope': 'https://www.googleapis.com/auth/drive.metadata.readonly',
                'include_granted_scopes': 'true',
                'state': 'pass-through value'};

  // Add form parameters as hidden input values.
  for (var p in params) {
    var input = document.createElement('input');
    input.setAttribute('type', 'hidden');
    input.setAttribute('name', p);
    input.setAttribute('value', params[p]);
    form.appendChild(input);
  }

  // Add form to page and submit it to open the OAuth 2.0 endpoint.
  document.body.appendChild(form);
  form.submit();
}
```

## Resource Owner Password Credentials
   1. 보안 취약
   2. 네이버 User +password 정보로 Server에 토큰 발행 요청하는 것


## Client Credentials "야! 내가 그냥 네이버야! 뭘 네이버한테 허락을 구해!"
   1. Oauth Provider <-> client
   2. 즉, (Server==OauthProvider) <-> client

----

# Spring security

# Client Credentials Grant Type 방식
![](https://velog.velcdn.com/images/blacklandbird/post/7e286b79-342b-4829-a87f-e792f1ede82a/image.png)

## 인증
1. Security Context (인메모리 세션저장소) 를 생성 >> SecurityContextHolder 에 저장
2. usernamePasswordAuthenticationFilter 인증
3. (인증 성공) SecurityContext 에 `UsernameAuthentication` + `Authentication` 저장
4. (토근 생성 성공) Session 에 SecurityContext 저장

## 권한
1. Session 에서 SecurityContext 를 꺼냄
2. SecurityContext 에서 `Authentication` 있으면, 인증된 유저.

# Security Context

![](https://velog.velcdn.com/images/mooh2jj/post/2aaad7fc-409b-4862-8577-7ee283dba929/image.png)

# Filters
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdG7K7O%2Fbtq3YLd75UT%2FDggosK4oiDRpxYclymDb90%2Fimg.png)

## UsernamePasswordAuthenticationFilter
- 인증되지 않은 사용자 리다이렉트 필터

#### UsernamePassword(form) 비활성화
```
// 5. form 로그인 해제 (UsernamePasswordAuthenticationFilter 비활성화)
http.formLogin().disable();
```

## BasicAuthenticationFilter
1. 헤더 `Basic` 인증 처리
2. SecurityContextHolder에 저장.

#### httpBasic 비활성화
```
// 6. username, password 헤더 로그인 방식 해제 (BasicAuthenticationFilter 비활성화)
http.httpBasic().disable();
```

## OncePerRequestFilter
- filter을 여러번 등록함에 따라, 여러번 호출됨 (이슈)
- 요청당 한번만 실행을 보장. (해결)

----
# 멘토님께 질문.
1. oauth 질문.
   - 회사 내부 API 통신 혹은 Mircro Service 간 JWT 인증 방식을 사용하시나요? 
     1. 단순 JWT 토큰 (서버키 사용)
     2. Client Credential 인증

