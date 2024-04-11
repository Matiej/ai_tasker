package com.emat.ai2rtasker.aidevstasker.aidevsapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiDevsTokenResponse {
    private int code;
    private String msg;
    private String token;

}
