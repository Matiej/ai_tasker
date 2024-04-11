package com.emat.ai2rtasker.aidevstasker.aidevsapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiDevsApiAnswerResponse {
    private int code;
    private String msg;
    private String note;
}
