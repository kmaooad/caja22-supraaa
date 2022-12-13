package edu.kmaooad.apiCommunication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
@RequiredArgsConstructor
public class OrgsWebClient {

    @Qualifier("organizationsWebClient")
    private final WebClient webClient;

    //fetch method stubs
    public Long fetchUserOrganizations(Long userId) {
//        perform http call to endpoint
//        this.webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("?? some endpoint/ ??" + userId)
//                        .build())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(Long[].class)
//                .block();
//        return new Long[] {1L, 2L, 3L};
        return 3L;
    }


    public Long fetchUserDepartments(Long userId) {
//        perform http call to endpoint
//        this.webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("?? some other endpoint/ ??" + userId)
//                        .build())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(Long[].class)
//                .block();
//        return new Long[] {4L, 5L, 6L};
        return 3L;
    }

}
