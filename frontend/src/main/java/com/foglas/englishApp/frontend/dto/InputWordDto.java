package com.foglas.englishApp.frontend.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Value
@Builder
@Getter
@Setter
@AllArgsConstructor
public class InputWordDto {


    @NotBlank(message = "Word have to be with text form")
    private String text;
    private String secondForm;
    private String thirdForm;
    private String countable;
    private List<ExampleDto> examples;
    private Long userId;



    @JsonCreator
    public static InputWordDto create(@JsonProperty("text") String text,
                                      @JsonProperty("secondForm") String secondForm,
                                      @JsonProperty("thirdForm") String thirdForm,
                                      @JsonProperty("countable") String countable,
                                      @JsonProperty("examples") List<ExampleDto> examples,
                                      @JsonProperty("examples") Long userId){
        return InputWordDto.builder()
                .text(text)
                .secondForm(secondForm)
                .thirdForm(thirdForm)
                .countable(countable)
                .examples(examples)
                .userId(userId)
                .build();
    }

}