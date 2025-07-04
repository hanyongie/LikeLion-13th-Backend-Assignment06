package com.likelion.likelionassignmentoauth.oauth.api.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class UserInfo {
    private String id;

    private String email;

    @SerializedName("verified_email")
    private Boolean verifiedEmail;

    private String name;

    @SerializedName("given_name")
    private String givenName;

    @SerializedName("family_name")
    private String familyName;

    @SerializedName("picture")
    private String pictureUrl;

    private String locale;
}
