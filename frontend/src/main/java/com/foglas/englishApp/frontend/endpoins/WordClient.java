package com.foglas.englishApp.frontend.endpoins;


import com.foglas.englishApp.dto.InputWordDto;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Log4j2
public class WordClient implements WordClientInf {

    @Override
    public void sendSave(InputWordDto inputWordDto) {
// Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL
        String url = "http://localhost:8080/englishApp/api/createWord";

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the request body as a JSON string
        String requestBody = "{\r\n" +
                "    \"text\": \"buy\",\r\n" +
                "    \"secondForm\": \"bought\",\r\n" +
                "    \"thirdForm\": \"bought\",\r\n" +
                "    \"countable\": \"C\",\r\n" +
                "    \"examples\": [\r\n" +
                "    {\r\n" +
                "        \"text\": \"I buy gasoline every month\"\r\n" +
                "    },\r\n" +
                "    {\r\n" +
                "        \"text\": \"I bought that one year ago\"\r\n" +
                "    }\r\n" +
                "    ]\r\n" +
                "}";

        // Wrap the request body and headers in HttpEntity
        HttpEntity<InputWordDto> requestEntity = new HttpEntity<>(inputWordDto, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Print the response body
        log.info(response.getBody());

    }

    @Override
    public List<InputWordDto> getWordSet(int count) {
        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL with path variable
        String url = "http://localhost:8080/englishApp/api/getSet/{id}";

        // Define the path parameter value (20 in this case)

        // Send GET request with the path variable
        ResponseEntity<List<InputWordDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                count
        );
        // Print the response body
        System.out.println(response.getBody());
        return response.getBody();
    }
}
