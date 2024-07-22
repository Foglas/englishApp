package com.foglas.project.englishApp.app.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_detail")
public class UserDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGen")
    @SequenceGenerator(name = "userGen", sequenceName = "userid",  allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name = "nickname")
    private String name;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_word", joinColumns = @JoinColumn(name = "fk_wordid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "fk_userid", referencedColumnName = "id"))
    private List<Word> words;

}
