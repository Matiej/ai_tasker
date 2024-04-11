package com.emat.ai2rtasker.aidevstasker.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskResponse {
    private int code;
    private String msg;
    private String token;
}
