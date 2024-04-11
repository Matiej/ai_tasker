package com.emat.ai2rtasker.aidevstasker.aidevsapi;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface AiDevsApiService {
    Mono<AiDevsTokenResponse> getToken(String apikey, String taskName);
    Mono<JsonNode> getTask(String token);
    Mono<AiDevsApiAnswerResponse> checkAnswer(String answer, String token);
    Mono<AiDevsApiAnswerResponse> checkAnswer(AiDevArrayRequest answer, String token);
}
