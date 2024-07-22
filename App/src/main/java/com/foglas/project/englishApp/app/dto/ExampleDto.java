package com.foglas.project.englishApp.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foglas.project.englishApp.app.domain.Example;
import lombok.*;

@Value
public class ExampleDto {
    private String text;

    @JsonCreator
    public ExampleDto(@JsonProperty("text") String text){
        this.text = text;
    }

}
