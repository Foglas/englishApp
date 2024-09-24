package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.dto.ExampleDto;
import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import com.foglas.englishApp.frontend.enums.Strategy;
import com.foglas.englishApp.frontend.components.interfaces.FormInf;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.RouteScopeOwner;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@UIScope
public class PractiseForm extends VerticalLayout implements FormInf {
    private Button buttonStart;
    private NumberField numberField;
    private ComboBox<Strategy> strategies;

    private CardDataProvider cardData;
    private WordClient wordClient;

    public PractiseForm(CardDataProvider cardData, WordClient wordClient){
        this.cardData = cardData;
        this.wordClient = wordClient;
        setWidthFull();
        setHeightFull();
        HorizontalLayout formHorLay = new HorizontalLayout();
        formHorLay.setWidthFull();
        formHorLay.setJustifyContentMode(JustifyContentMode.CENTER);

        FormLayout form = new FormLayout();
        form.setWidth(60f, Unit.PERCENTAGE);
        setAlignItems(Alignment.CENTER);


        strategies = new ComboBox("Strategy of finding words");
        strategies.setItems(Strategy.values());
        strategies.setItemLabelGenerator((strategy)-> strategy.name());
        form.add(strategies);

        numberField = new NumberField("Count");
        form.add(numberField);
        formHorLay.add(form);


        HorizontalLayout buttonHorLay = new HorizontalLayout();
        buttonHorLay.setWidth(60f, Unit.PERCENTAGE);
        buttonHorLay.setJustifyContentMode(JustifyContentMode.START);
        buttonStart = new Button("Start");
        buttonStart.getStyle().set("margin-left","2em");
        clickStartHandle();

        buttonHorLay.add(buttonStart);

        add(formHorLay, buttonHorLay);
    }

    private void clickStartHandle(){
        buttonStart.addClickListener(buttonClickEvent -> {
            cardData.setWords(wordClient.getWordSet(numberField.getValue().intValue()));
            UI.getCurrent().navigate("api/cards");
        });
    }

    @Override
    public Map<String, String> readValues() {
        Map<String, String> map = new HashMap<>();
        map.put("wordsCount", numberField.getValue().toString());
        map.put("strategy", strategies.getValue().name());
        return map;
    }


}
