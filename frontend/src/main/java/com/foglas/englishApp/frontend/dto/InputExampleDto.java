package com.foglas.englishApp.frontend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class InputExampleDto {
    private Long id;
    private String text;

    @JsonCreator
    public static InputExampleDto create(@JsonProperty("id") Long id,
                                   @JsonProperty("text") String text) {
        return new InputExampleDto(id, text);
    }
}
