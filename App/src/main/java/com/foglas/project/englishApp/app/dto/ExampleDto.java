package com.foglas.project.englishApp.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foglas.project.englishApp.app.domain.Example;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Value
public class ExampleDto {

    @NotBlank(message = "Example have to be at least one word")

    private String text;

    @JsonCreator
    public ExampleDto(@JsonProperty("text") String text){
        this.text = text;
    }

}
