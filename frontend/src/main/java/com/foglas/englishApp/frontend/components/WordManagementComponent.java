package com.foglas.englishApp.frontend.components;


import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.dto.ExampleDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.spring.annotation.UIScope;
import io.swagger.v3.oas.models.examples.Example;
import org.checkerframework.checker.guieffect.qual.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UIScope
@Component
public class WordManagementComponent extends Div {
    //private WordService wordService;

    //private List<InputWordDto> people = wordService;
    @Autowired
    public WordManagementComponent(WordService wordService) {
        setWidth(70, Unit.PERCENTAGE);
        setHeight(80, Unit.PERCENTAGE);
        VirtualList<InputWordDto> list = new VirtualList<>();
        List<InputWordDto> words = Arrays.asList(
                new InputWordDto(
                        "run", "ran", "run", "no",
                        Arrays.asList(new ExampleDto("I run every morning.")),
                        null),
                new InputWordDto(
                        "eat", "ate", "eaten", "yes",
                        Arrays.asList(new ExampleDto("I eat breakfast at 8 AM.")),
                        null),
                new InputWordDto(
                        "go", "went", "gone", "no",
                        Arrays.asList(new ExampleDto("She goes to school every day.")),
                        null),
                new InputWordDto(
                        "see", "saw", "seen", "no",
                        Arrays.asList(new ExampleDto("I saw a movie last night.")),
                        null),
                new InputWordDto(
                        "take", "took", "taken", "yes",
                        Arrays.asList(new ExampleDto("She takes an umbrella when it rains.")),
                        null),
                new InputWordDto(
                        "speak", "spoke", "spoken", "no",
                        Arrays.asList(new ExampleDto("They spoke about the project during the meeting.")),
                        null),
                new InputWordDto(
                        "write", "wrote", "written", "no",
                        Arrays.asList(new ExampleDto("She writes a letter every Sunday.")),
                        null),
                new InputWordDto(
                        "buy", "bought", "bought", "yes",
                        Arrays.asList(new ExampleDto("I bought a new car last weekend.")),
                        null),
                new InputWordDto(
                        "make", "made", "made", "yes",
                        Arrays.asList(new ExampleDto("He made a delicious cake.")),
                        null),
                new InputWordDto(
                        "begin", "began", "begun", "no",
                        Arrays.asList(new ExampleDto("The concert began at 7 PM.")),
                        null)
        );

        list.setItems(words);
        list.setRenderer(personCardRenderer);
        list.setWidthFull();
        list.setHeightFull();
        add(list);
    }

    private ComponentRenderer<com.vaadin.flow.component.Component, InputWordDto> personCardRenderer = new ComponentRenderer<>(
            word -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                VerticalLayout infoLayout = new VerticalLayout();
                infoLayout.setWidthFull();
                infoLayout.setWidthFull();

                infoLayout.setSpacing(false);
                infoLayout.setPadding(false);
                infoLayout.getElement().appendChild(
                        ElementFactory.createStrong());
                infoLayout.add(new Div(new Text(word.getText())));

                VerticalLayout contactLayout = new VerticalLayout();
                contactLayout.setSpacing(false);
                contactLayout.setPadding(false);
                contactLayout.add(new Div(new Text(word.getSecondForm())));
                contactLayout
                        .add(new Div(new Text(word.getThirdForm())));
                infoLayout
                        .add(new Details("Contact information", contactLayout));

                cardLayout.add(infoLayout);
                return cardLayout;
            });
}
