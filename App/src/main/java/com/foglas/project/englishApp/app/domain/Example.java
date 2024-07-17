package com.foglas.project.englishApp.app.domain;

import jakarta.persistence.*;

@Table(name = "Example")
public class Example {

    @Id
    @SequenceGenerator(name = "exampleGen", sequenceName = "exampleId",initialValue = 1)
    private long id;

    @Column(name = "text")
    private String text;
}
