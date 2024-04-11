package com.emat.ai2rtasker.aidevstasker.application;

import com.emat.ai2rtasker.aidevstasker.controllers.AnsCommand;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface TaskService {

    Mono<AidevsTaskerResponse> getTokenAndTask(String taskName);
    Mono<TaskResponse> getToken(String taskName);
    Mono<JsonNode> getTask(String token);

    Mono<AnswerResponse> checkAnswer(AnsCommand answer, String token);
}
