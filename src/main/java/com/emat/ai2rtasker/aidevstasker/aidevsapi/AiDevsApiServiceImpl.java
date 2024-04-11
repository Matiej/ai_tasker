package com.emat.ai2rtasker.aidevstasker.aidevsapi;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AiDevsApiServiceImpl implements AiDevsApiService {
    @Value(value = "${aidevs.api.url.token}")
    private String aidevsUlr;
    private WebClient webClient;

    private final WebClient.Builder webClientBuilder;

    public AiDevsApiServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostConstruct
    private void init() {
        this.webClient = webClientBuilder
                .baseUrl(aidevsUlr)
                .filter(WebClientLoggingFilter.logRequest())
                .filter(WebClientLoggingFilter.logResponse())
                .build();
    }

    @Override
    public Mono<AiDevsTokenResponse> getToken(String apikey, String taskName) {
        return this.webClient.post()
                .uri("token/" + taskName)
                .body(Mono.just(new AiDevsTokenRequest(apikey)), AiDevsTokenRequest.class)
                .retrieve()
                .bodyToMono(AiDevsTokenResponse.class);
    }

    @Override
    public Mono<JsonNode> getTask(String token) {
        return this.webClient.get()
                .uri("task/" + token)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    @Override
    public Mono<AiDevsApiAnswerResponse> checkAnswer(String answer, String token) {
        return this.webClient.post()
                .uri("answer/" + token)
                .body(Mono.just(new AiDevsApiAnswerRequest(answer)), AiDevsApiAnswerRequest.class)
                .retrieve()
                .bodyToMono(AiDevsApiAnswerResponse.class);
    }

    @Override
    public Mono<AiDevsApiAnswerResponse> checkAnswer(AiDevArrayRequest answer, String token) {
        return this.webClient.post()
                .uri("answer/" + token)
                .body(Mono.just(answer), AiDevArrayRequest.class)
                .retrieve()
                .bodyToMono(AiDevsApiAnswerResponse.class);
    }


}
