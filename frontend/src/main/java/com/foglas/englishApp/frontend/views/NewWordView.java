package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.components.WordForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("api/word/add")
public class NewWordView extends MyAppLayout {

 private final WordForm wordForm;

    public NewWordView(){
        VerticalLayout verticalLayout = new VerticalLayout();
        this.wordForm = new WordForm();
        verticalLayout.add(wordForm);
        setContent(verticalLayout);

    }
}
