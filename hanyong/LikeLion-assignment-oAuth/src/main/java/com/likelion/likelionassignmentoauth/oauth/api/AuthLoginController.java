package com.likelion.likelionassignmentoauth.oauth.api;

import com.likelion.likelionassignmentoauth.oauth.api.dto.Token;
import com.likelion.likelionassignmentoauth.oauth.application.AuthLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
@Slf4j
public class AuthLoginController {
    private final AuthLoginService authLoginService;

    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;

    private final String GOOGLE_REDIRECT_URI = "https://hanyong00.shop/login/oauth2/code/google";

/*
    @GetMapping("/code/{registrationID}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationID){
        authLoginService.socialLogin(code,registrationID);
    }*/


    @GetMapping("/code/google")
    public Token googleCallback(@RequestParam(name = "code")String code){
        log.info("[Google OAuth] Authorization code: {}", code);
        log.info("[Google OAuth] 요청할 client_id: {}", GOOGLE_CLIENT_ID);
        log.info("[Google OAuth] 요청할 redirect_uri: {}", GOOGLE_REDIRECT_URI);
        String googleAccessToken = authLoginService.getGoogleAccessToken(code);
        return loginOrSignup(googleAccessToken);
    }
    public Token loginOrSignup(String googleAccessToken){
        return authLoginService.loginOrSignUp(googleAccessToken);
    }


}
