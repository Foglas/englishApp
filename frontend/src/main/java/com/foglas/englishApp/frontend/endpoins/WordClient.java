package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dto.ExerciseDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.foglas.englishApp.frontend.dto.PriorityDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Log4j2
public class WordClient implements WordClientInf {
    private AuthenticationProvider authenticationProvider;

    @Override
    public Mono<String> sendSave(InputWordDto inputWordDto, String token) {
        // Create WebClient instance
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/englishApp/api/private") // Base URL
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Set the Authorization header
                .build();

        // Send POST request
       return webClient.post()
                .uri("/createWord") // Specify the endpoint
                .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set Content-Type header
                .bodyValue(inputWordDto) // Set request body
                .retrieve() // Initiates the request and retrieves the response
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        this::handleErrorResponse // Handle the error response
                )
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> sendUpdate(OutputWordDto word, String token) {
        // Create WebClient instance
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/englishApp/api/private") // Base URL
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Set the Authorization header
                .build();

        // Send POST request
        return webClient.post()
                .uri("/updateWord") // Specify the endpoint
                .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set Content-Type header
                .bodyValue(word) // Set request body
                .retrieve() // Initiates the request and retrieves the response
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        this::handleErrorResponse // Handle the error response
                )
                .bodyToMono(String.class);
    }

    @Override
    public List<OutputWordDto> getWordSet(int count, String token, Long userId) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        // Define the URL with path variable
        String url = "http://localhost:8080/englishApp/api/private/getSet/"+count+"/"+userId;


        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


        // Send GET request with the path variable
        ResponseEntity<List<OutputWordDto>> response = restTemplate.exchange(
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

    @Override
    public Mono<ExerciseDto> increasePriority(PriorityDto priorityDto, String token) {
        // Create WebClient instance
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/englishApp/api/private") // Base URL
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Set the Authorization header
                .build();

        // Send POST request
        return webClient.post()
                .uri("/priority/plus") // Specify the endpoint
                .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set Content-Type header
                .bodyValue(priorityDto) // Set request body
                .retrieve() // Initiates the request and retrieves the response
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        this::handleErrorResponse // Handle the error response
                )
                .bodyToMono(ExerciseDto.class);
    }

    @Override
    public Mono<ExerciseDto> decreasePriority(PriorityDto priorityDto, String token) {
        // Create WebClient instance
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/englishApp/api/private") // Base URL
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Set the Authorization header
                .build();

        // Send POST request
        return webClient.post()
                .uri("/priority/minus") // Specify the endpoint
                .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set Content-Type header
                .bodyValue(priorityDto) // Set request body
                .retrieve() // Initiates the request and retrieves the response
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        this::handleErrorResponse // Handle the error response
                )
                .bodyToMono(ExerciseDto.class);
    }

    public List<OutputWordDto> getAllWordsByUserId(Long userId, String token){
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        // Define the URL with path variable
        String url = "http://localhost:8080/englishApp/api/private/words/user/"+userId;


        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


        // Send GET request with the path variable
        ResponseEntity<List<OutputWordDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        // Print the response body
        System.out.println(response.getBody());
        return response.getBody();
    }

    public Mono<String> deleteWordById(Long wordId, String token){
        // Create WebClient instance
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/englishApp/api/private") // Base URL
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // Set the Authorization header
                .build();

        // Send POST request
        return webClient.delete()
                .uri("/words/delete/"+wordId) // Specify the endpoint
                .header(HttpHeaders.CONTENT_TYPE, "application/json") // Set Content-Type header
                .retrieve() // Initiates the request and retrieves the response
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(), // Check for client/server errors
                        this::handleErrorResponse // Handle the error response
                )
                .bodyToMono(String.class);
    }


    private Mono<? extends Throwable> handleErrorResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    System.out.println("Error response: " + errorBody);
                    return Mono.error(new RuntimeException("Error response: " + errorBody));
                });
    }
}
