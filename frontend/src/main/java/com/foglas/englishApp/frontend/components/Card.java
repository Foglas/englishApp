package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.components.interfaces.CardInf;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.ArrayList;
import java.util.List;


@Tag("Card")
@Setter
@Getter
@Log4j2
public class Card extends Div implements CardInf {

    private H2 wordInOneLanguage;
    private H2 wordInSecondLanguage;
    private H3 wordForms;
    private List<Paragraph> paragraphs;
    private VerticalLayout mainVerticalLayout;
    private CardType currentType;
    private InputWordDto inputWordDto;
    private HorizontalLayout buttonBar;

    private Button correctButton;
    private Button badButton;


    public Card(InputWordDto inputWordDto, CardType type){
        mainVerticalLayout = new VerticalLayout();
        this.wordInOneLanguage = new H2(inputWordDto.getText());
        this.wordInSecondLanguage = new H2("bought");
        this.paragraphs = new ArrayList<>();
        mainVerticalLayout = new VerticalLayout();
        currentType = type;
        this.inputWordDto = inputWordDto;


        inputWordDto.getExamples().forEach(
                example -> paragraphs.add(new Paragraph(example.getText()))
        );

        setClassName("card");

        switch(type) {
           case ANSWER -> {
               initCardAnswer(inputWordDto);
               initQuestionStyle();
               add(mainVerticalLayout);
           }
           case QUESTION -> {
               initCardQuestion(inputWordDto.getText());
               add(mainVerticalLayout);
           }
        }

        buttonBar = generateQuestionButtons();

        initStyles();
    }


    private void initStyles(){
        setWidth(20, Unit.EM);
        setHeight(26, Unit.EM);
        getStyle().set("background-color", "hsla(214, 61%, 25%, 0.05)");
        getStyle().set("border-radius", "1em");
        getStyle().set("margin-top", "5em");
        mainVerticalLayout.getStyle().set("margin-top","5em");
        mainVerticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
      }

    private void initQuestionStyle(){
        wordForms.getStyle().set("font-size", "15px");
    }


    private void initCardAnswer(InputWordDto word){

        //added form of the words
        List<String > forms = new ArrayList<>();
        forms.add(word.getSecondForm());
        forms.add(word.getThirdForm());
        this.wordForms = generateFormsInfo(forms);

       // buttonBar = generateQuestionButtons();

        mainVerticalLayout.add(this.wordInOneLanguage, this.wordInSecondLanguage, this.wordForms, buttonBar);

        for (Paragraph paragraph: paragraphs){
            mainVerticalLayout.add(paragraph);
        }
    }

    private void initCardQuestion(String text){
        addClickListener(clickEvent -> {
            if (currentType == CardType.QUESTION) {
                flip();
            }
        });

        wordInOneLanguage = new H2(text);
        mainVerticalLayout.add(wordInOneLanguage);
     }

    private void clear(){
       mainVerticalLayout.removeAll();
       if (buttonBar != null && currentType.equals(CardType.ANSWER)) {
           buttonBar.removeAll();
       }
    }



    private HorizontalLayout generateQuestionButtons(){
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        horizontalLayout.setHeight(20f, Unit.PERCENTAGE);
        Icon correct = VaadinIcon.CHECK_CIRCLE.create();
        correct.setColor("green");
        Icon bad = VaadinIcon.CLOSE_CIRCLE.create();
        bad.setColor("red");

        badButton = new Button(bad);
        correctButton = new Button(correct);

        log.info("correctButton " + correctButton);

        Icon questionMark = VaadinIcon.QUESTION.create();
        horizontalLayout.add(badButton,questionMark, correctButton);
        return horizontalLayout;
    }


    private H3 generateFormsInfo(List<String> forms){
        H3 row = new H3("[");
        String text = row.getText();

        for(int i = 0; i<forms.size(); i++){
            String form = forms.get(i);
            if (i< (forms.size()-1)){
                text += " " + form + ",";
            } else {
                text += " " + form;
            }
        }
        row.setText(text + "]");
        return row;
    }




    /**
     * will flip the card and show correct answers
     */
    @Override
    public void flip(){

        UI.getCurrent().access(() -> {
            clear();
                    switch (currentType) {
                        case ANSWER -> {
                            initCardQuestion(inputWordDto.getText());
                            currentType = CardType.QUESTION;
                        }

                        case QUESTION -> {
                            initCardAnswer(inputWordDto);
                            currentType = CardType.ANSWER;
                        }
                    }
                    UI.getCurrent().push();
                });

    }

    @Override
    public void setActionToCorrAnswBtn(Runnable operation) {
        correctButton.addClickListener(buttonClickEvent -> operation.run());
    }

    @Override
    public void setActionToBadAnswBtn(Runnable operation) {
        badButton.addClickListener(buttonClickEvent -> operation.run());
    }
}
