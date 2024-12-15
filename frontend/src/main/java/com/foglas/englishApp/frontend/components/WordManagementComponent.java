package com.foglas.englishApp.frontend.components;


import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.dto.ExampleDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.spring.annotation.UIScope;
import io.swagger.v3.oas.models.examples.Example;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.MurmurHash3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UIScope
@Component
@Log4j2
public class WordManagementComponent extends Div {
    //private WordService wordService;
    private UI ui = UI.getCurrent();

    //private List<InputWordDto> people = wordService;
    @Autowired
    public WordManagementComponent(WordService wordService) {
        setWidth(70, Unit.PERCENTAGE);
        setHeight(80, Unit.PERCENTAGE);
        VirtualList<InputWordDto> list = new VirtualList<>();
        List<InputWordDto> words = new ArrayList<>();

        list.setItems(words);
        list.setRenderer(personCardRenderer);
        list.setWidthFull();
        list.setHeightFull();
        add(list);
    }

    private ComponentRenderer<com.vaadin.flow.component.Component, InputWordDto> personCardRenderer = new ComponentRenderer<>(
            word -> {
                HorizontalLayout cardLayout = new HorizontalLayout();

                cardLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                cardLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
                cardLayout.setMargin(true);
                cardLayout.setWidthFull();
                cardLayout.setWidthFull();
                cardLayout.setSpacing(true);

                Div headerAndExample = new Div();
                headerAndExample.addClickListener((divClickEvent -> handleDetails()));
                headerAndExample.getElement().getStyle().set("cursor", "pointer");
                H3 wordText = new H3(word.getText());
                wordText.getStyle().set("margin-left", "0.4em");
                wordText.getStyle().set("margin-right", "0.4em");
                wordText.getStyle().set("font-weight", "bold");
                HorizontalLayout information = new HorizontalLayout();
                information.setWidth(50, Unit.PERCENTAGE);

                Paragraph text = new Paragraph(word.getExamples().getFirst().getText());
                text.getStyle().set("margin-left", "0.4em");
                text.getStyle().set("margin-right", "0.4em");
                text.getStyle().set("margin-top", "0em");
                text.getStyle().set("margin-bottom", "0em");

                headerAndExample.add(wordText, text);
                information.add(headerAndExample);


                HorizontalLayout buttons = new HorizontalLayout();
                buttons.setWidth(30, Unit.PERCENTAGE);
                buttons.setAlignItems(FlexComponent.Alignment.CENTER);
                buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
                buttons.setMargin(true);
                buttons.setWidthFull();
                buttons.setWidthFull();
                buttons.setSpacing(true);

                Button delete = new Button("DELETE");
                buttons.add(delete);

                Button edit = new Button("EDIT");
                edit.getStyle().set("margin-right", "0.8em");

                buttons.add(edit);

                cardLayout.add(information, buttons);

                return cardLayout;
            });


         private void handleDetails(){
             ui.access(()-> ui.navigate("word/detail"));
         }

}
