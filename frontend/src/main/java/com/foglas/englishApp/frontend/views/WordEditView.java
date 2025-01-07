package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.WordForm;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route("api/word/edit")
public class WordEditView extends MyAppLayout {
    private VaadinSession session = VaadinSession.getCurrent();
    @Autowired
    public WordEditView(WordForm wordForm, UserService userService, AuthenticationProvider authenticationProvider, CardDataProvider cardDataProvider){
        super(userService, authenticationProvider);
        VerticalLayout verticalLayout = new VerticalLayout();
        //      this.wordForm = new WordForm(wordService, authenticationProvider);
        wordForm.fillForm(cardDataProvider.getActualWord(session));
        verticalLayout.add(wordForm);
        setContent(verticalLayout);

    }
}
