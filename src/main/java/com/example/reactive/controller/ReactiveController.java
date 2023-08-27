package com.example.reactive.controller;

import com.example.reactive.dto.ReactiveDto;
import com.example.reactive.dto.TestRecord;
import com.example.reactive.services.TestRecordsService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reactive")
@Slf4j
@RequiredArgsConstructor
public class ReactiveController {

    private final TestRecordsService testRecordsService;

    @GetMapping
    public Mono<ReactiveDto> get() {
        return Mono.just(new ReactiveDto(UUID.randomUUID().toString(), "name"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Observed(
        name = "reactive.name",
        contextualName = "reactive-name"
    )
    public Mono<Map<String, String>> test(@RequestBody TestRecord object) {
        return testRecordsService.save(object)
            .map(savedId -> Map.of("id", String.valueOf(savedId)));
    }

}
