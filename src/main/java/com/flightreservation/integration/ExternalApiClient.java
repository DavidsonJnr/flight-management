package com.flightreservation.integration;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExternalApiClient {

    private final WebClient.Builder webClientBuilder;

    public <T> List<T> post(String baseUrl, String path, Object body, Class<T> responseType, Duration timeout) {
        return webClientBuilder.baseUrl(baseUrl).build()
                .post()
                .uri(path)
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(responseType)
                .collectList()
                .block(timeout);
    }

}

