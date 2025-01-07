package com.foglas.englishApp.frontend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@AllArgsConstructor
@Getter
@Setter
public class ResultDto {
    private Integer success;
    private Integer failed;

    @JsonCreator
    public static ResultDto create(@JsonProperty("success") Integer success,
                                   @JsonProperty("failed") Integer failed) {
        return new ResultDto(success, failed);
    }
}
