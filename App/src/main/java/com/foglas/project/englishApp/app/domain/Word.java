package com.foglas.project.englishApp.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.catalina.User;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@Entity
@Table(name = "word")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wordGen")
    @SequenceGenerator(name = "wordGen", sequenceName = "wordid", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name = "text")
    @NotBlank(message = "Word have to be with text form")
    private String text;

    @Builder.Default
    @Column(name = "second_form")
    private String secondForm = "notExist";

    @Builder.Default
    @Column(name = "third_form")
    private String thirdForm = "notExist";

    @Builder.Default
    @Column(name = "countable")
    private String countable = "noSet";

    @Builder.Default
    @Column(name = "priority")
    private int priority = 10;

    @Builder.Default
    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    private List<Example> examples = new ArrayList<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_word", joinColumns = @JoinColumn(name = "fk_userid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "fk_wordid", referencedColumnName = "id"))
    private List<UserDetail> users = new ArrayList<>();

    public Word(int priority){
        this.priority = priority;
    }

    @Builder(builderMethodName = "builderFullWord")
    public static WordBuilder builderFullWord(Word word){
       return  Word.builder()
               .id(word.getId())
               .text(word.getText())
               .countable(word.getCountable())
               .secondForm(word.getSecondForm())
               .thirdForm(word.getThirdForm())
               .priority(word.getPriority())
               .examples(word.getExamples())
               .users(word.getUsers());
    }

    @Builder(builderMethodName = "builderFullWord")
    public static WordBuilder builderFullWordWithCustomPriority(Word word, int priority){
        return  Word.builder()
                .id(word.getId())
                .text(word.getText())
                .countable(word.getCountable())
                .secondForm(word.getSecondForm())
                .thirdForm(word.getThirdForm())
                .priority(priority)
                .examples(word.getExamples())
                .users(word.getUsers());
    }

    @Builder(builderMethodName = "builderWordSeparateExamples")
    public static WordBuilder builderWordSeparateExamples(Word word, List<Example> examples){
        return Word.builder()
                .id(word.getId())
                .text(word.getText())
                .countable(word.getCountable())
                .secondForm(word.getSecondForm())
                .thirdForm(word.getThirdForm())
                .priority(word.getPriority())
                .examples(examples)
                .users(word.getUsers());
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
                ", users=" + users +
                '}';
    }
}
