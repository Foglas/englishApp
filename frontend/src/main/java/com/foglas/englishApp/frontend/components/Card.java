package com.foglas.englishApp.frontend.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Tag("Card")
@Setter
@Getter
public class Card extends Div {

    private H2 wordInOneLanguage;
    private H2 wordInSecondLanguage;
    private List<Paragraph> paragraphs;



    public Card(String wordInOneLanguage, String wordInSecondLanguage){
        this.wordInOneLanguage = new H2();
        this.wordInSecondLanguage = new H2();
        this.paragraphs = new ArrayList<>();
        setClassName("card");

        initCard(wordInOneLanguage, wordInSecondLanguage);
        initStyles();

    }


    private void initStyles(){
        setWidth(20, Unit.EM);
        setHeight(26, Unit.EM);
        getStyle().set("background-color", "hsla(214, 61%, 25%, 0.05)");
        getStyle().set("border-radius", "1em");
        getStyle().set("margin-top", "5em");
    }


    private void initCard(String wordInOneLanguage, String wordInSecondLanguage){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.getStyle().set("margin-top","5em");
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        this.wordInOneLanguage.setText(wordInOneLanguage);
        this.wordInSecondLanguage.setText(wordInSecondLanguage);
        verticalLayout.add(this.wordInOneLanguage, this.wordInSecondLanguage);
        Icon questionMark = VaadinIcon.QUESTION.create();
        verticalLayout.add(questionMark);
        add(verticalLayout);
    }

    /**
     * will flip the card and show correct answers
     */
    public Card changeOnAction(String wordInOneLanguage, String wordInSecondLanguage){
    return new Card(wordInSecondLanguage, wordInOneLanguage);
    }
}
