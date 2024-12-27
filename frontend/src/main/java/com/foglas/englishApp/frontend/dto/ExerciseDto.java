package com.foglas.englishApp.frontend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ExerciseDto {
    private long id;

    @JsonCreator
    public static ExerciseDto create(@JsonProperty("id") long id){
     return new ExerciseDto(id);
    }
}

