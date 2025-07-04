package com.likelion.likelionassignmentoauth.oauth.application;

import com.google.gson.Gson;
import com.likelion.likelionassignmentoauth.global.jwt.JwtTokenProvider;
import com.likelion.likelionassignmentoauth.member.domain.Member;
import com.likelion.likelionassignmentoauth.member.domain.Role;
import com.likelion.likelionassignmentoauth.member.domain.repository.MemberRepository;
import com.likelion.likelionassignmentoauth.oauth.api.dto.Token;
import com.likelion.likelionassignmentoauth.oauth.api.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthLoginService {
/*
    // 컨트롤러에서 받은 authorization code와 소셜 로그인 id를 받아 콘솔에 출력
   public void socialLogin(String code, String registrationId){
       System.out.println("code = " + code);
       System.out.println("registrationId = " + registrationId);
    }*/

    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    // 구글 인증 코드를 엑세스 토큰으로 교환하는 API 주소
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    // OAuth 인증 후 구글이 리디렉션할 URI
    private final String GOOGLE_REDIRECT_URI = "https://hanyong00.shop/login/oauth2/code/google";
//    private final String GOOGLE_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";


    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String getGoogleAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = Map.of(
                "code", code,
                "scope", "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email",
                "client_id", GOOGLE_CLIENT_ID,
                "client_secret", GOOGLE_CLIENT_SECRET,
                "redirect_uri", GOOGLE_REDIRECT_URI,
                "grant_type", "authorization_code"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();

            return gson.fromJson(json, Token.class)
                    .getAccessToken();
        }

        log.error("구글 토큰 요청 실패: {}", responseEntity);
        throw new RuntimeException("구글 엑세스 토큰을 가져오는데 실패했습니다.");
    }

    public Token loginOrSignUp(String googleAccessToken){
        UserInfo userInfo = getUserInfo(googleAccessToken);

        if(!userInfo.getVerifiedEmail()){
            throw new RuntimeException("이메일 인증이 되지 않은 유저입니다.");
        }

        Member member = memberRepository.findByEmail(userInfo.getEmail()).orElseGet(() ->
                memberRepository.save(Member.builder()
                        .email(userInfo.getEmail())
                        .name(userInfo.getName())
                        .pictureUrl(userInfo.getPictureUrl())
                        .role(Role.ROLE_USER)
                        .build())
        );

        String jwt = jwtTokenProvider.generateToken(member);
        return new Token(jwt);
    }

    public UserInfo getUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            String json = responseEntity.getBody();
            Gson gson = new Gson();
            return gson.fromJson(json, UserInfo.class);
        }

        throw new RuntimeException("유저 정보를 가져오는데 실패했습니다.");
    }

}