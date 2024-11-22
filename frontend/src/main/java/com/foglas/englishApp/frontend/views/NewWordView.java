package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.components.WordForm;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("api/word/add")
public class NewWordView extends MyAppLayout {

 private final WordForm wordForm;

    @Autowired
    public NewWordView(WordService wordService, UserService userService, AuthenticationProvider authenticationProvider){
        super(userService, authenticationProvider);
        VerticalLayout verticalLayout = new VerticalLayout();
        this.wordForm = new WordForm(wordService, authenticationProvider);
        verticalLayout.add(wordForm);
        setContent(verticalLayout);

    }
}
