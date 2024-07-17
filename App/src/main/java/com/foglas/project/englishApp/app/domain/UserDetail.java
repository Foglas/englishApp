package com.foglas.project.englishApp.app.domain;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "UserDetail")
public class UserDetail {


    @Id
    @SequenceGenerator(name = "userGen", sequenceName = "userId",initialValue = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_wordId")
    private List<Word> words;

}
