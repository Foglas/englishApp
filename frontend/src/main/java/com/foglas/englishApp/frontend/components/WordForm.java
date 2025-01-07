package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.interfaces.FormInf;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.foglas.englishApp.frontend.dto.ExampleDto;
import com.foglas.englishApp.frontend.dto.InputExampleDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.foglas.englishApp.frontend.enums.Countable;
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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UIScope
@Component
@Log4j2
public class WordForm extends VerticalLayout implements FormInf {

    private VaadinSession session = VaadinSession.getCurrent();
    private UI ui = UI.getCurrent();
    private AuthenticationProvider authenticationProvider;
    private CardDataProvider cardDataProvider;
    private FormLayout formLayout;
    private TextField text;
    private TextField originalText;
    private TextField secondForm;
    private TextField thirdForm;
    private ComboBox<Countable> countable;
    private Button buttonSave;
    private Button buttonCancel;
    private List<TextField> examples;
    private VerticalLayout wrapperExamples = new VerticalLayout();
    private WordService wordService;
    private boolean isEditable = false;

    public WordForm(WordService wordService, AuthenticationProvider authenticationProvider, CardDataProvider cardDataProvider) {
        this.wordService = wordService;
        this.authenticationProvider = authenticationProvider;
        this.cardDataProvider = cardDataProvider;
        this.formLayout = new FormLayout();
        this.text = new TextField("Text", "take");
        this.originalText = new TextField("Original text", "vzít");
        this.secondForm = new TextField("Second form of the word", "took");
        this.thirdForm = new TextField("Third form of the word", "taken");
        this.countable = new ComboBox<>("Countable");
        this.buttonCancel = new Button("Cancel");
        this.buttonSave = new Button("save");
        examples = new ArrayList<>();
        countable.setItems(Countable.values());
        countable.setValue(Countable.NOT_STATED);
        countable.setItemLabelGenerator((item) -> {
            if (item.equals(Countable.NOT_STATED)) {
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

        for (int i = 0; i < examples.size(); i++) {
            TextField example = examples.get(i);
            mapOfValues.put("example" + i, example.getValue());
        }

        return mapOfValues;
    }

    private InputWordDto toInputWordDto() {
        String text = this.text.getValue();
        String secondForm = this.secondForm.getValue();
        String thirdForm = this.thirdForm.getValue();
        String countable = this.countable.getValue().name();
        String originalText = this.originalText.getValue();
        List<ExampleDto> examples = new ArrayList<>();
        for (int i = 0; i < this.examples.size(); i++) {
            TextField example = this.examples.get(i);
            examples.add(new ExampleDto(example.getValue()));
        }

        return InputWordDto.builder()
                .text(text)
                .examples(examples)
                .secondForm(secondForm)
                .thirdForm(thirdForm)
                .countable(countable)
                .userId(authenticationProvider.getUserId(session))
                .originalText(originalText)
                .build();

    }

    private OutputWordDto toOutputWordDto() {
        String text = this.text.getValue();
        String secondForm = this.secondForm.getValue();
        String thirdForm = this.thirdForm.getValue();
        String countable = this.countable.getValue().name();
        String originalText = this.originalText.getValue();
        List<InputExampleDto> examples = new ArrayList<>();
        for (int i = 0; i < this.examples.size(); i++) {
            TextField example = this.examples.get(i);
            if (i< examples.size()) {
                examples.add(new InputExampleDto(cardDataProvider.getActualWord(session).getExamples().get(i).getId(), example.getValue()));
            } else {
                examples.add(new InputExampleDto(null, example.getValue()));

            }
        }

        return OutputWordDto.builder()
                .text(text)
                .examples(examples)
                .secondForm(secondForm)
                .thirdForm(thirdForm)
                .countable(countable)
                .userId(authenticationProvider.getUserId(session))
                .originalText(originalText)
                .id(cardDataProvider.getActualWord(session).getId())
                .build();
    }

    public void clickSaveHandle() {
        buttonSave.addClickListener((event) -> {
            Map<String, String> mapOfValues = readValues();
            mapOfValues.forEach((name, value) -> {
                log.info("Form: " + name + " : " + value);
            });
            if (isEditable) {
                wordService.updateWord(toOutputWordDto(), authenticationProvider.getToken(session)).subscribe(
                        response -> {
                            ui.access(() -> {
                                Notification.show("Word was successful updated", 3000, Notification.Position.BOTTOM_CENTER);
                                clearForm();
                                ui.push();
                                ui.navigate("words");
                            });
                        },
                        error -> ui.access(() -> Notification.show(error.getMessage().split("\"response\":")[1].replace("\"}", "").replace("\"", "").replace("}", "").replace("{", ""), 3000, Notification.Position.BOTTOM_CENTER))
                );
                isEditable = false;
            } else {
                wordService.saveWord(toInputWordDto(), authenticationProvider.getToken(session)).subscribe(
                        response -> {
                            ui.access(() -> {
                                Notification.show("Word was successful created", 3000, Notification.Position.BOTTOM_CENTER);
                                clearForm();
                                ui.push();
                                ui.navigate("words");
                            });

                        },
                        error -> ui.access(() -> Notification.show(error.getMessage().split("\"response\":")[1].replace("\"}", "").replace("\"", "").replace("}", "").replace("{", ""), 3000, Notification.Position.BOTTOM_CENTER))
                );
            }

        });

    }

    public void clickCancelHandle() {
        buttonCancel.addClickListener((event) -> {
            buttonCancel.getUI().ifPresent((ui) -> ui.navigate("/words"));
        });
    }

    private void clearForm() {
        text.setValue("");
        secondForm.setValue("");
        thirdForm.setValue("");
        originalText.setValue("");
        countable.setValue(Countable.NOT_STATED);
        wrapperExamples.removeAll();
        examples.clear();
        wrapperExamples.add(createExample());

    }

    private void initFormLayout() {
        formLayout.add(text, originalText, secondForm, thirdForm, countable);
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
        buttonSave.getStyle().set("margin-left", "2em");
        buttonsHorLay.add(buttonSave);

        buttonCancel.setWidth(5f, Unit.EM);
        buttonCancel.getStyle().set("margin-left", "1em");
        buttonsHorLay.add(buttonCancel);

        wrapperExamples.add(createExample());


        verticalWrapper.add(formHorLay, wrapperExamples, buttonsHorLay);
        horizontalWrapper.add(verticalWrapper);

        add(horizontalWrapper);
    }


    private HorizontalLayout createExample() {
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
                        wrapperExamples.add(createExample());
                        UI.getCurrent().push();
                    }
            );
        });

        Button buttonMinus = new Button(VaadinIcon.MINUS.create());
        buttonMinus.addClickListener(buttonClickEvent1 -> {
                    if (examples.size() > 1) {
                        wrapperExamples.remove(layout);
                        UI.getCurrent().access(() -> {
                            examples.remove(example);
                            UI.getCurrent().push();
                        });

                    } else {
                        Notification.show("You have to fill the example",3000, Notification.Position.BOTTOM_CENTER);
                    }
                }
        );

        layout.add(example);
        layout.expand(example);
        layout.add(buttonAdd);
        layout.add(buttonMinus);
        return layout;
    }

    private HorizontalLayout createExampleWithText(String text) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setDefaultVerticalComponentAlignment(Alignment.END);
        layout.setWidthFull();
        TextField example = new TextField("Example");
        examples.add(example);
        example.setValue(text);
        example.setWidthFull();
        Button buttonAdd = new Button(VaadinIcon.PLUS.create());
        buttonAdd.addClickListener(buttonClickEvent -> {
            UI.getCurrent().access(
                    () -> {
                        wrapperExamples.add(createExample());
                        UI.getCurrent().push();
                    }
            );
        });

        Button buttonMinus = new Button(VaadinIcon.MINUS.create());
        buttonMinus.addClickListener(buttonClickEvent1 -> {
                    if (examples.size() > 1) {
                        wrapperExamples.remove(layout);
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

    public void fillForm(OutputWordDto word) {
        isEditable = true;
        text.setValue(word.getText());
        originalText.setValue(word.getOriginalText());
        secondForm.setValue(word.getSecondForm());
        thirdForm.setValue(word.getThirdForm());
        switch (word.getCountable()) {
            case "C" -> countable.setValue(Countable.C);
            case "U" -> countable.setValue(Countable.U);
            case "NOT_STATED" -> countable.setValue(Countable.NOT_STATED);
        }
        examples.clear();
        wrapperExamples.removeAll();
        for (InputExampleDto example : word.getExamples()) {
            wrapperExamples.add(createExampleWithText(example.getText()));
        }
    }
}
