package com.foglas.project.englishApp.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Getter
public class InputWordDto {

    private String text;
    private String secondForm;
    private String thirdForm;
    private String countable;
    //private List<Example> examples;
    //private List<User> users;

    public InputWordDto() {
    }
}
