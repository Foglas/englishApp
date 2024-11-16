package com.foglas.englishApp.frontend.endpoins;


import com.foglas.englishApp.dto.InputWordDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Log4j2
public class WordClient implements WordClientInf {

    private String token = "eyJhbGciOssiJIUzI1NiJ9.eyJzdWIiOiJyYWRvc3RAZW1haWwuY3oiLCJpYXQiOjE3MzE3NTIwMjgsImV4cCI6MTczMTc1NTYyOH0.m5Eo7qUit8pCGZSwPNSgdwX1HWcYYWrvmmIJxMmUfaA";

    @Override
    public void sendSave(InputWordDto inputWordDto) {
        // Create WebClient instance
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/englishApp/api/private") // Base URL
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Set the Authorization header
                .build();

        // Send POST request
        webClient.post()
                .uri("/createWord") // Specify the endpoint
                .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set Content-Type header
                .bodyValue(inputWordDto) // Set request body
                .retrieve() // Initiates the request and retrieves the response
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        clientResponse -> handleErrorResponse(clientResponse) // Handle the error response
                )
                .bodyToMono(String.class) // Parse the response body as a String
                .doOnTerminate(() -> System.out.println("Request completed")) // Optional: for logging
                .subscribe(responseBody -> {
                    // Handle the response (e.g., print the response body)
                    System.out.println("Response: " + responseBody);
                });
    }

    @Override
    public List<InputWordDto> getWordSet(int count) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        // Define the URL with path variable
        String url = "http://localhost:8080/englishApp/api/private/getSet";


        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


        // Send GET request with the path variable
        ResponseEntity<List<InputWordDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {},
                count
        );

        // Print the response body
        System.out.println(response.getBody());
        return response.getBody();
    }

    private Mono<? extends Throwable> handleErrorResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    System.out.println("Error response: " + errorBody);
                    return Mono.error(new RuntimeException("Error response: " + errorBody));
                });
    }
}
