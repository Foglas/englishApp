package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dto.InputUserDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.LoginDTO;
import com.foglas.englishApp.frontend.dto.RegisterDTO;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient implements UserClientInf {

    @Override
    public Mono<InputUserDto> login(LoginDTO loginDTO) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
            return webClient.post()
                    .uri("/englishApp/api/public/user/login")
                    .header("Content-Type", "application/json")
                    .bodyValue(loginDTO)
                    .retrieve()
                    .bodyToMono(InputUserDto.class);
    }

    @Override
    public void logout() {
        UI.getCurrent().push();
    }

    @Override
    public Mono<String> register(RegisterDTO registerDTO) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        Mono<String> response = webClient.post()
                .uri("/englishApp/api/public/user/register")
                .header("Content-Type", "application/json")
                .bodyValue(registerDTO)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        this::handleErrorResponse)
                .bodyToMono(String.class);


        return response;
    }

    private Mono<? extends Throwable> handleErrorResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    System.out.println("Error response: " + errorBody);
                    return Mono.error(new RuntimeException("Error response: " + errorBody));
                });
    }
}
