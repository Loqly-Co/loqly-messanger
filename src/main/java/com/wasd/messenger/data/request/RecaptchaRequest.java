package com.wasd.messenger.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecaptchaRequest {
    private String secret;
    private String response;
    private String remoteip;
}