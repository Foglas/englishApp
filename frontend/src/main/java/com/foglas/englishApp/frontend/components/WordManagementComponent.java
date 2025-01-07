package com.foglas.englishApp.frontend.components;


import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@UIScope
@Component
@Log4j2
public class WordManagementComponent extends Div implements AfterNavigationObserver {

    private final WordService wordService;
    private final AuthenticationProvider authenticationProvider;
    private CardDataProvider dataProvider;
    private UI ui = UI.getCurrent();
    private VaadinSession session = VaadinSession.getCurrent();
    private List<OutputWordDto> words;
    VirtualList<OutputWordDto> list;
    @Autowired
    public WordManagementComponent(WordService wordService, AuthenticationProvider authenticationProvider, CardDataProvider dataProvider) {
        setWidth(70, Unit.PERCENTAGE);
        setHeight(80, Unit.PERCENTAGE);

        this.wordService = wordService;
        this.authenticationProvider = authenticationProvider;
        this.dataProvider = dataProvider;
    }

    private ComponentRenderer<com.vaadin.flow.component.Component, OutputWordDto> personCardRenderer = new ComponentRenderer<>(
            word -> {
                HorizontalLayout cardLayout = new HorizontalLayout();

                cardLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                cardLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
                cardLayout.setMargin(true);
                cardLayout.setWidthFull();
                cardLayout.setWidthFull();
                cardLayout.setSpacing(true);

                Div headerAndExample = new Div();
                headerAndExample.addClickListener((divClickEvent -> handleDetails()));
                headerAndExample.getElement().getStyle().set("cursor", "pointer");
                H3 wordText = new H3(word.getText());
                wordText.getStyle().set("margin-left", "0.4em");
                wordText.getStyle().set("margin-right", "0.4em");
                wordText.getStyle().set("font-weight", "bold");
                HorizontalLayout information = new HorizontalLayout();
                information.setWidth(50, Unit.PERCENTAGE);

                Paragraph text = new Paragraph(word.getExamples().getFirst().getText());
                text.getStyle().set("margin-left", "0.4em");
                text.getStyle().set("margin-right", "0.4em");
                text.getStyle().set("margin-top", "0em");
                text.getStyle().set("margin-bottom", "0em");

                headerAndExample.add(wordText, text);
                information.add(headerAndExample);


                HorizontalLayout buttons = new HorizontalLayout();
                buttons.setWidth(30, Unit.PERCENTAGE);
                buttons.setAlignItems(FlexComponent.Alignment.CENTER);
                buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
                buttons.setMargin(true);
                buttons.setWidthFull();
                buttons.setHeightFull();
                buttons.setSpacing(true);

                Button delete = new Button("DELETE");
                delete.addClickListener(buttonClickEvent -> {
                    handleDelete(word.getId());
                });
                buttons.add(delete);

                Button edit = new Button("EDIT");
                edit.getStyle().set("margin-right", "0.8em");
                edit.addClickListener(addClickListener ->{
                   dataProvider.saveActualWord(word, session);
                   UI.getCurrent().navigate("api/word/edit");
                });
                buttons.add(edit);

                cardLayout.add(information, buttons);

                return cardLayout;
            });


    private void handleDetails() {
        ui.access(() -> ui.navigate("word/detail"));
    }

    private void handleDelete(Long wordId) {
        UI.getCurrent().access(() -> {
            wordService.deleteWordById(wordId, authenticationProvider.getToken(session)).subscribe();
            words.removeIf((word)-> word.getId() == wordId);
            Notification.show("Word is successfully deleted", 3000, Notification.Position.BOTTOM_CENTER);
        });
        updateList();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        updateList();
    }

    private void updateList(){
        if (list != null) {
            remove(list);
        }
        list = new VirtualList<>();
        words = wordService.allWordsByUserId(authenticationProvider.getUserId(session), authenticationProvider.getToken(session));
        list.setItems(words);
        list.setRenderer(personCardRenderer);
        list.setWidthFull();
        list.setHeightFull();

        add(list);
    }
}
