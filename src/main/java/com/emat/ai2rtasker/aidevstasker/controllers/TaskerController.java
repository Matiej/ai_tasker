package com.emat.ai2rtasker.aidevstasker.controllers;

import com.emat.ai2rtasker.aidevstasker.application.AidevsTaskerResponse;
import com.emat.ai2rtasker.aidevstasker.application.AnswerResponse;
import com.emat.ai2rtasker.aidevstasker.application.TaskService;
import com.emat.ai2rtasker.aidevstasker.application.TaskResponse;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task grabber API", description = "API designed for.")
public class TaskerController {
    private final TaskService taskService;

    public TaskerController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping(value = "/task-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameter(name = "taskName", required = true, description = "Task name you want to get token, automatically you will get all task&token")
    Mono<ResponseEntity<AidevsTaskerResponse>> getTokenAndTask(@RequestParam(value = "taskName") String taskName) {
        Mono<AidevsTaskerResponse> responseMono = taskService.getTokenAndTask(taskName);
        return responseMono
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameter(name = "taskName", required = true, description = "Task name you want to get token")
    Mono<ResponseEntity<TaskResponse>> getAuToken(@RequestParam(value = "taskName") String taskName) {
        Mono<TaskResponse> taskResponse = taskService.getToken(taskName);
        return taskResponse
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping(value = "/task")
    @Parameter(name = "token", required = true, description = "Token you got form token uri)")
    Mono<ResponseEntity<JsonNode>> getTask(@RequestParam(value = "token") String token) {
        return taskService.getTask(token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @PostMapping(value = "/answer")
    @Operation(summary = "Sending an answer to aiDevs server", description = "Send answer, depends what they need, array or simple string. One of it is mandatory, and of course TOKEN")
    @Parameter(name = "token", required = true, description = "Use token you got before")
    Mono<ResponseEntity<AnswerResponse>> checkAnswerForObject(@RequestBody(required = false) AnsCommand answer, @RequestParam(value = "token", required = false) String token) {
        if (answer.getAnswerArray().length == 0 && Strings.isBlank(answer.getAnswerString())) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(new AnswerResponse(1, "Bad Request - answer is empty", "Need at least one parameter for answer")));
        } else if (answer.getAnswerArray().length > 0 && Strings.isNotBlank(answer.getAnswerString())) {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(new AnswerResponse(1, "Bad Request - you are trying 2 parameters", "Need at least one parameter for answer not two. Decide for one and come back")));
        }
        return taskService.checkAnswer(answer, token)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping(value = "/ping")
    ResponseEntity<?> ping() {
        return ResponseEntity.ok("PING OK");
    }

}
