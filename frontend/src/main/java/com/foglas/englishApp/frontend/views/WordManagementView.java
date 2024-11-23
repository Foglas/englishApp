package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.WordManagementComponent;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@Route("words")
public class WordManagementView extends MyAppLayout {

    private UI ui = UI.getCurrent();
    private WordService wordService;

    @Autowired
    public WordManagementView(WordService wordService, UserService userService, AuthenticationProvider authenticationProvider, WordManagementComponent wordManagementComponent) {
        super(userService, authenticationProvider);
        this.wordService = wordService;
        VerticalLayout wrapper = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button createWord = new Button("Create word");
        createWord.addClickListener((e) -> handleCreateWord());
        buttons.add(createWord);
        wrapper.add(buttons);

        wrapper.setSizeFull();
        wrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        wrapper.add(wordManagementComponent);
        setContent(wrapper);
    }

    private void handleCreateWord(){
        ui.access(()-> ui.navigate("api/word/add"));
    }


}
