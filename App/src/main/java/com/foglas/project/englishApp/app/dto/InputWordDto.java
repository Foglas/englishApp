package com.foglas.project.englishApp.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foglas.project.englishApp.app.domain.Example;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Value
@Builder
public class InputWordDto {


    @NotBlank(message = "Word have to be with text form")
    private String text;

    private String secondForm;
    private String thirdForm;
    private String countable;
    private List<ExampleDto> examples;

    @JsonCreator
    public static InputWordDto create(@JsonProperty("text") String text,
                                      @JsonProperty("secondForm") String secondForm,
                                      @JsonProperty("thirdForm") String thirdForm,
                                      @JsonProperty("countable") String countable,
                                      @JsonProperty("examples") List<ExampleDto> examples){
        return InputWordDto.builder()
                .text(text)
                .secondForm(secondForm)
                .thirdForm(thirdForm)
                .countable(countable)
                .examples(examples)
                .build();
    }

}
