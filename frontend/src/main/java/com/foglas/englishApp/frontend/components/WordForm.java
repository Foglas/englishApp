package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.components.interfaces.FormInf;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.HashMap;
import java.util.Map;


public class WordForm extends VerticalLayout implements FormInf {

    private FormLayout formLayout;
    private TextField text;

    private TextField secondForm;

    private TextField thirdForm;

    private TextField countable;

    private Button buttonSave;
    private Button buttonCancel;


    public WordForm(){
        this.formLayout = new FormLayout();
        this.text = new TextField("Text", "take");
        this.secondForm = new TextField("Second form of the word", "took");
        this.thirdForm = new TextField("Third form of the word", "taken");
        this.countable = new TextField("Countable", "C,U or not Stated");
        this.buttonCancel = new Button("Cancel");
        this.buttonSave = new Button("save");

        initFormLayout();
        addActionToSaveButtons();
        addActionToCancelButtons();
    }

    @Override
    public Map<String, String> readValues() {
        Map<String, String> mapOfValues = new HashMap<>();
        mapOfValues.put("text", text.getValue());
        mapOfValues.put("secondForm", secondForm.getValue());
        mapOfValues.put("thirdForm", thirdForm.getValue());
        mapOfValues.put("countable", countable.getValue());
        return mapOfValues;
    }

    @Override
    public void addActionToSaveButtons() {
        buttonSave.addClickListener((event) ->{
        Map<String, String> mapOfValues = readValues();

        });
    }

    @Override
    public void addActionToCancelButtons() {
        buttonCancel.addClickListener((event) ->{
            buttonCancel.getUI().ifPresent((ui) -> ui.navigate("api/cards"));
        });
    }

    private void initFormLayout(){
        formLayout.add(text,secondForm,thirdForm,countable);
        setWidthFull();

        HorizontalLayout horizontalWrapper = new HorizontalLayout();
        horizontalWrapper.setWidthFull();
        horizontalWrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        VerticalLayout verticalWrapper = new VerticalLayout();
        verticalWrapper.setSizeUndefined();


        HorizontalLayout formHorLay = new HorizontalLayout(formLayout);
        formHorLay.add(formLayout);

        HorizontalLayout buttonsHorLay = new HorizontalLayout();
        buttonSave.setWidth(5f, Unit.EM);
        buttonSave.getStyle().set("margin-left","2em");
        buttonsHorLay.add(buttonSave);

        buttonCancel.setWidth(5f, Unit.EM);
        buttonCancel.getStyle().set("margin-left","1em");
        buttonsHorLay.add(buttonCancel);

        verticalWrapper.add(formHorLay,buttonsHorLay);
        horizontalWrapper.add(verticalWrapper);
        add(horizontalWrapper);
    }


}
