package com.foglas.englishApp.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PriorityDto {
    private Long wordId;
    private Long exerciseId;
}
