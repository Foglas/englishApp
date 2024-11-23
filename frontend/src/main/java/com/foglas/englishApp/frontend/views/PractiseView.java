package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.PractiseForm;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/practise")
@UIScope
public class PractiseView extends MyAppLayout {


    @Autowired
    public PractiseView(PractiseForm practiseForm,CardDataProvider cardData, WordService wordService, UserService userService, AuthenticationProvider authenticationProvider){
        super(userService, authenticationProvider);

       // setContent(new PractiseForm(cardData, wordService, authenticationProvider));
        setContent(practiseForm);
    }
}
