package com.programmers.musicapp.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}

