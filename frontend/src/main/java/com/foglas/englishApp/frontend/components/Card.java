package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.components.interfaces.CardInf;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Tag("Card")
@Setter
@Getter
public class Card extends Div implements CardInf {

    private H2 wordInOneLanguage;
    private H2 wordInSecondLanguage;
    private H3 wordForms;
    private List<Paragraph> paragraphs;

    
    private VerticalLayout mainVerticalLayout;


    public Card(String wordInOneLanguage, String wordInSecondLanguage){
        this.wordInOneLanguage = new H2();
        this.wordInSecondLanguage = new H2();
        this.paragraphs = new ArrayList<>();

        paragraphs.add(new Paragraph("he take me to the school"));
        paragraphs.add(new Paragraph("he took a taxi 30 minutes ago"));

        setClassName("card");

        initCard(wordInOneLanguage, wordInSecondLanguage);
        initStyles();
        changeToAnswer();
    }


    private void initStyles(){
        setWidth(20, Unit.EM);
        setHeight(26, Unit.EM);
        getStyle().set("background-color", "hsla(214, 61%, 25%, 0.05)");
        getStyle().set("border-radius", "1em");
        getStyle().set("margin-top", "5em");
        wordForms.getStyle().set("font-size", "15px");
    }


    private void initCard(String wordInOneLanguage, String wordInSecondLanguage){
        mainVerticalLayout = new VerticalLayout();
        mainVerticalLayout.getStyle().set("margin-top","5em");
        mainVerticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        this.wordInOneLanguage.setText(wordInOneLanguage);
        this.wordInSecondLanguage.setText(wordInSecondLanguage);
        List<String > forms = new ArrayList<>();
        forms.add("took");
        forms.add("taken");
        this.wordForms = generateFormRow(forms);
        mainVerticalLayout.add(this.wordInOneLanguage, this.wordInSecondLanguage, this.wordForms);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Icon questionMark = VaadinIcon.QUESTION.create();
        Icon correct = VaadinIcon.CHECK_CIRCLE.create();
        correct.setColor("green");
        Icon bad = VaadinIcon.CLOSE_CIRCLE.create();
        bad.setColor("red");

        Button btnCorrAnsw = new Button(correct);
        Button btnBadAnswer = new Button(bad);

        horizontalLayout.add(btnBadAnswer,questionMark, btnCorrAnsw);
        mainVerticalLayout.add(horizontalLayout);

        for (Paragraph paragraph: paragraphs){
            mainVerticalLayout.add(paragraph);
        }

        add(mainVerticalLayout);
    }


    private H3 generateFormRow(List<String> forms){
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
    public void changeToAnswer(){
       mainVerticalLayout.remove(mainVerticalLayout.getChildren().toList());


        this.wordInOneLanguage.setText("take");
        this.wordInSecondLanguage.setText("took");
        List<String > forms = new ArrayList<>();
        forms.add("took");
        forms.add("taken");
        this.wordForms = generateFormRow(forms);
        mainVerticalLayout.add(this.wordInOneLanguage, this.wordInSecondLanguage, this.wordForms);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Icon questionMark = VaadinIcon.QUESTION.create();
        Icon correct = VaadinIcon.CHECK_CIRCLE.create();
        correct.setColor("green");
        Icon bad = VaadinIcon.CLOSE_CIRCLE.create();
        bad.setColor("red");

        Button btnSuccess = new Button(correct);
        Button btnBadAnswer = new Button(bad);

        horizontalLayout.add(btnBadAnswer,questionMark, btnSuccess);
        mainVerticalLayout.add(horizontalLayout);

        for (Paragraph paragraph: paragraphs){
            mainVerticalLayout.add(paragraph);
        }

        add(mainVerticalLayout);
    }

    @Override
    public void setActionToCorrAnswBtn() {

    }

    @Override
    public void setActionToBadAnswBtn() {

    }
}
