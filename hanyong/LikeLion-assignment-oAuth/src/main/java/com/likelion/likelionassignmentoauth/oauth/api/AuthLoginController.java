package com.likelion.likelionassignmentoauth.oauth.api;

import com.likelion.likelionassignmentoauth.oauth.api.dto.Token;
import com.likelion.likelionassignmentoauth.oauth.application.AuthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
public class AuthLoginController {
    private final AuthLoginService authLoginService;


/*
    @GetMapping("/code/{registrationID}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationID){
        authLoginService.socialLogin(code,registrationID);
    }*/


    @GetMapping("/code/google")
    public Token googleCallback(@RequestParam(name = "code")String code){
        String googleAccessToken = authLoginService.getGoogleAccessToken(code);
        return loginOrSignup(googleAccessToken);
    }
    public Token loginOrSignup(String googleAccessToken){
        return authLoginService.loginOrSignUp(googleAccessToken);
    }


}
