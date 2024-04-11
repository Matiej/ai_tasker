package com.emat.ai2rtasker.aidevstasker.application;

import com.emat.ai2rtasker.aidevstasker.aidevsapi.AiDevsTokenResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AidevsTaskerResponse  {
    private TaskResponse taskResponse;
    private JsonNode task;
}
