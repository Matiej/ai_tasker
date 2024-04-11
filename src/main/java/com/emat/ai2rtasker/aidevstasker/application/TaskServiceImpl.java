package com.emat.ai2rtasker.aidevstasker.application;

import com.emat.ai2rtasker.aidevstasker.aidevsapi.AiDevArrayRequest;
import com.emat.ai2rtasker.aidevstasker.aidevsapi.AiDevsApiAnswerResponse;
import com.emat.ai2rtasker.aidevstasker.aidevsapi.AiDevsApiService;
import com.emat.ai2rtasker.aidevstasker.aidevsapi.AiDevsTokenResponse;
import com.emat.ai2rtasker.aidevstasker.controllers.AnsCommand;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Value(value = "${my.ai2r.token}")
    private String apiKey;
    private final AiDevsApiService aiDevsApiService;

    @Override
    public Mono<AidevsTaskerResponse> getTokenAndTask(String taskName) {
        return this.getToken(taskName).flatMap(tokenResp ->
                getTask(tokenResp.getToken())
                        .map(task -> new AidevsTaskerResponse(tokenResp, task)));
    }

    @Override
    public Mono<TaskResponse> getToken(String taskName) {
        Mono<AiDevsTokenResponse> aiDevsTokenResponse = aiDevsApiService.getToken(apiKey, taskName);
        return aiDevsTokenResponse.map(apiRes -> new TaskResponse(apiRes.getCode(), apiRes.getMsg(), apiRes.getToken()))
                .onErrorResume(error -> Mono.just(new TaskResponse(1, error.getMessage(), "ERROR RESPONSE")));
    }

    @Override
    public Mono<JsonNode> getTask(String token) {
        return aiDevsApiService.getTask(token);
    }

    @Override
    public Mono<AnswerResponse> checkAnswer(AnsCommand answer, String token) {
        Mono<AiDevsApiAnswerResponse> aiDevsApiAnswerResponseMono;
        if (answer.getAnswerArray().length > 0) {
            aiDevsApiAnswerResponseMono = aiDevsApiService.checkAnswer(new AiDevArrayRequest(answer.getAnswerArray()), token);
        } else {
            aiDevsApiAnswerResponseMono = aiDevsApiService.checkAnswer(answer.getAnswerString(), token);
        }
//        Mono<AiDevsApiAnswerResponse> aiDevsApiAnswerResponseMono = aiDevsApiService.checkAnswer(new AiDevArrayRequest(answer.getAnswer()), token);
        return aiDevsApiAnswerResponseMono.map(apiRes -> new AnswerResponse(apiRes.getCode(), apiRes.getMsg(), apiRes.getNote()))
                .onErrorResume(err -> Mono.just(new AnswerResponse(1, err.getMessage(), "ERROR RESPONSE")));
    }
}
