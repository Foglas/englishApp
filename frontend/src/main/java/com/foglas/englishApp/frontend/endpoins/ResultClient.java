package com.foglas.englishApp.frontend.endpoins;


import com.foglas.englishApp.frontend.dto.ResultDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ResultClient {


    public List<ResultDto> getAllResultsByUserId(Long userId, String token) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        // Define the URL with path variable
        String url = "http://localhost:8080/englishApp/api/private/exerciseResults/user/"+userId;


        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);


        // Send GET request with the path variable
        ResponseEntity<List<ResultDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        // Print the response body
        System.out.println(response.getBody());
        return response.getBody();
    }
}
