package com.foglas.englishApp.frontend.components;

import ch.qos.logback.core.Layout;
import com.foglas.englishApp.frontend.components.interfaces.FormInf;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinConfig;
import lombok.extern.log4j.Log4j2;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;



@Log4j2
public class WordForm extends VerticalLayout implements FormInf {

    private FormLayout formLayout;
    private TextField text;

    private TextField secondForm;

    private TextField thirdForm;

    private TextField countable;

    private Button buttonSave;
    private Button buttonCancel;
    private List<TextField> examples;


    public WordForm(){
        this.formLayout = new FormLayout();
        this.text = new TextField("Text", "take");
        this.secondForm = new TextField("Second form of the word", "took");
        this.thirdForm = new TextField("Third form of the word", "taken");
        this.countable = new TextField("Countable", "C,U or not Stated");
        this.buttonCancel = new Button("Cancel");
        this.buttonSave = new Button("save");
        examples = new ArrayList<>();

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

        for(int i=0; i<examples.size(); i++){
            TextField example = examples.get(i);
            mapOfValues.put("example"+i, example.getValue());
        }

        return mapOfValues;
    }

    @Override
    public void addActionToSaveButtons() {
        buttonSave.addClickListener((event) ->{
        Map<String, String> mapOfValues = readValues();
            mapOfValues.forEach((name, value) -> {
                log.info("Form: " + name + " : " + value);
            });
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

        VerticalLayout examplesLayout = new VerticalLayout();
        examplesLayout.add(createExample(examplesLayout));


        verticalWrapper.add(formHorLay, examplesLayout,buttonsHorLay);
        horizontalWrapper.add(verticalWrapper);

        add(horizontalWrapper);
    }


    private HorizontalLayout createExample(VerticalLayout wrapper){
        HorizontalLayout layout = new HorizontalLayout();
            layout.setDefaultVerticalComponentAlignment(Alignment.END);
        layout.setWidthFull();
        TextField example = new TextField("Example");
        examples.add(example);
        example.setPlaceholder("Please enter the uses in a sentence");
        example.setWidthFull();
        Button buttonAdd = new Button(VaadinIcon.PLUS.create());
        buttonAdd.addClickListener(buttonClickEvent -> {
            UI.getCurrent().access(
                    ()->{
                        wrapper.add(createExample(wrapper));
                        UI.getCurrent().push();
                    }
            );
        });

        Button buttonMinus = new Button(VaadinIcon.MINUS.create());
        buttonMinus.addClickListener(buttonClickEvent1 -> {
                    if (examples.size() > 1){
                        wrapper.remove(layout);
                        UI.getCurrent().access(()->{
                            examples.remove(example);
                            UI.getCurrent().push();
                        });

                    } else {
                        Notification.show("You have to fill the example");
                    }
                }
        );

        layout.add(example);
        layout.expand(example);
        layout.add(buttonAdd);
        layout.add(buttonMinus);
        return layout;
    }
}
