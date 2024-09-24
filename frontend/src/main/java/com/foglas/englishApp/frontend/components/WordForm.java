package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.dto.ExampleDto;
import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import com.foglas.englishApp.frontend.enums.Countable;
import com.foglas.englishApp.frontend.components.interfaces.FormInf;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.log4j.Log4j2;

import java.util.*;


@Log4j2
public class WordForm extends VerticalLayout implements FormInf {

    private FormLayout formLayout;
    private TextField text;
    private TextField secondForm;
    private TextField thirdForm;
    private ComboBox<Countable> countable;
    private Button buttonSave;
    private Button buttonCancel;
    private List<TextField> examples;
    private WordClient wordClient;

    public WordForm(WordClient wordClient){
        this.wordClient = wordClient;
        this.formLayout = new FormLayout();
        this.text = new TextField("Text", "take");
        this.secondForm = new TextField("Second form of the word", "took");
        this.thirdForm = new TextField("Third form of the word", "taken");
        this.countable = new ComboBox<>("Countable");
        this.buttonCancel = new Button("Cancel");
        this.buttonSave = new Button("save");
        examples = new ArrayList<>();
        countable.setItems(Countable.values());
        countable.setItemLabelGenerator((item)-> {
        if (item.equals(Countable.NOT_STATED)){
            return "Not stated";
        } else {
            return item.name();
        }
        });

        initFormLayout();
        clickSaveHandle();
        clickCancelHandle();
    }

    @Override
    public Map<String, String> readValues() {
        Map<String, String> mapOfValues = new HashMap<>();
        mapOfValues.put("text", text.getValue());
        mapOfValues.put("secondForm", secondForm.getValue());
        mapOfValues.put("thirdForm", thirdForm.getValue());
        mapOfValues.put("countable", countable.getValue().name());

        for(int i=0; i<examples.size(); i++){
            TextField example = examples.get(i);
            mapOfValues.put("example"+i, example.getValue());
        }

        return mapOfValues;
    }

    private InputWordDto toDto(){
     String text = this.text.getValue();
     String secondForm = this.secondForm.getValue();
     String thirdForm = this.thirdForm.getValue();
     String countable = this.countable.getValue().name();
     List<ExampleDto> examples = new ArrayList<>();
        for(int i=0; i<this.examples.size(); i++){
            TextField example = this.examples.get(i);
            examples.add(new ExampleDto(example.getValue()));
        }

        return InputWordDto.builder().text(text)
                .examples(examples)
                .secondForm(secondForm)
                .thirdForm(thirdForm)
                .countable(countable)
                .build();

    }

    public void clickSaveHandle() {
        buttonSave.addClickListener((event) ->{
        Map<String, String> mapOfValues = readValues();
            mapOfValues.forEach((name, value) -> {
                log.info("Form: " + name + " : " + value);
            });
            wordClient.sendSave(toDto());
        });
    }

    public void clickCancelHandle() {
        buttonCancel.addClickListener((event) ->{
            buttonCancel.getUI().ifPresent((ui) -> ui.navigate("/practise"));
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


    private HorizontalLayout createExample(VerticalLayout wrapper) {
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
                    () -> {
                        wrapper.add(createExample(wrapper));
                        UI.getCurrent().push();
                    }
            );
        });

        Button buttonMinus = new Button(VaadinIcon.MINUS.create());
        buttonMinus.addClickListener(buttonClickEvent1 -> {
                    if (examples.size() > 1) {
                        wrapper.remove(layout);
                        UI.getCurrent().access(() -> {
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
