package com.foglas.englishApp.frontend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ExampleDto {

    @NotBlank(message = "Example have to be at least one word")
    private String text;


    @JsonCreator
    public ExampleDto(@JsonProperty("text") String text){
        this.text = text;
    }

}