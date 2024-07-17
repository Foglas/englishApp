package com.foglas.project.englishApp.app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.List;

@Getter
@AllArgsConstructor
public class Word {

    @Id
    @SequenceGenerator(name = "WordGen", sequenceName = "wordId",initialValue = 1)
    private long id;

    @Column(name = "text")
    private String text;

    @Column(name = "secondForm")
    private String secondForm;

    @Column(name = "thirdForm")
    private String thirdForm;

    @Column(name = "countable")
    private String countable;

    @Column(name = "priority")
    private String priority;

    @Column(name = "examples")
    private List<Example> examples;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_userId")
    private List<User> users;

    private final int DEFAULT_PRIORITY = 10;

    public Word(){

    }
    public Word(String text, String secondForm, String thirdForm, String countable, List<Example> examples, List<User> users) {
        this.text = text;
        this.secondForm = secondForm;
        this.thirdForm = thirdForm;
        this.countable = countable;
        this.examples = examples;
        this.users = users;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", secondForm='" + secondForm + '\'' +
                ", thirdForm='" + thirdForm + '\'' +
                ", countable='" + countable + '\'' +
                ", priority='" + priority + '\'' +
                ", examples=" + examples +
                ", users=" + users +
                ", DEFAULT_PRIORITY=" + DEFAULT_PRIORITY +
                '}';
    }
}
