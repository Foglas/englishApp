package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.components.enums.Strategy;
import com.foglas.englishApp.frontend.components.interfaces.FormInf;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;

import java.util.HashMap;
import java.util.Map;


public class PractiseForm extends VerticalLayout implements FormInf {
    private Button buttonStart;
    private NumberField numberField;
    private ComboBox<Strategy> strategies;


    public PractiseForm(){
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
            Notification.show("Start :) " + readValues());
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
