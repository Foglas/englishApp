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
public class OutputWordDto {


    @NotBlank(message = "Word have to be with text form")
    private String text;
    private String secondForm;
    private String thirdForm;
    private String countable;
    private List<InputExampleDto> examples;
    private Long userId;
    private String originalText;
    private long id;




    @JsonCreator
    public static OutputWordDto create(@JsonProperty("text") String text,
                                      @JsonProperty("secondForm") String secondForm,
                                      @JsonProperty("thirdForm") String thirdForm,
                                      @JsonProperty("countable") String countable,
                                      @JsonProperty("examples") List<InputExampleDto> examples,
                                      @JsonProperty("userId") Long userId,
                                      @JsonProperty("originalText") String originalText,
                                      @JsonProperty("wordId") long id){
        return OutputWordDto.builder()
                .text(text)
                .secondForm(secondForm)
                .thirdForm(thirdForm)
                .countable(countable)
                .examples(examples)
                .userId(userId)
                .originalText(originalText)
                .id(id)
                .build();
    }

}
