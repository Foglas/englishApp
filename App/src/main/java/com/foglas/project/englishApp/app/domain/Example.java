package com.foglas.project.englishApp.app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "example")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class Example {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exampleGen")
    @SequenceGenerator(name = "exampleGen", sequenceName = "exampleid", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "fk_wordid", referencedColumnName = "id")
    private Word word;
    public Example(String text, Word word){
        this.text = text;
        this.word = word;
    }
}
