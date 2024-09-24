package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.components.WordForm;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("api/word/add")
public class NewWordView extends MyAppLayout {

 private final WordForm wordForm;

    public NewWordView(WordClient wordClient){
        VerticalLayout verticalLayout = new VerticalLayout();
        this.wordForm = new WordForm(wordClient);
        verticalLayout.add(wordForm);
        setContent(verticalLayout);

    }
}
