package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.WordForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("api/word/add")
public class NewWordView extends VerticalLayout {

 private final WordForm wordForm;

    public NewWordView(){
        this.wordForm = new WordForm();
        setSizeFull();
        add(wordForm);

    }
}
