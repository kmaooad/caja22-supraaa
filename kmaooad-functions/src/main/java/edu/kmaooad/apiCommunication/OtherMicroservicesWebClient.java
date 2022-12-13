package edu.kmaooad.apiCommunication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OtherMicroservicesWebClient {

    private final WebClient webClient;

    public Mono<String> sendPostRequest(String functionUrl, String fullCommand) {
        return this.webClient.post()
                .uri(functionUrl)
                .body(Mono.just(fullCommand), String.class)
                .accept(MediaType.TEXT_PLAIN)
                .exchangeToMono(r -> {
                    if (r.statusCode().equals(HttpStatus.OK)) {
                        return r.bodyToMono(String.class);
                    } else if (r.statusCode().is4xxClientError()) {
                        return Mono.just("Error response with code " + r.statusCode());
                    } else {
                        return r.createException().flatMap(Mono::error);
                    }
                });
    }
}
