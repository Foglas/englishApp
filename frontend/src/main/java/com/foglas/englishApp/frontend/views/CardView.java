package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.dto.ExampleDto;
import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.components.Card;
import com.foglas.englishApp.frontend.components.CardType;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Route(value = "api/cards")
@Log4j2
@UIScope
public class CardView extends MyAppLayout {

    private CardDataProvider cardData;

    public CardView(CardDataProvider cardData){
        this.cardData = cardData;
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        verticalLayout.add(horizontalLayout);
        setContent(verticalLayout);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        initCards(horizontalLayout, cardData.getWords());
    }

    private void initCards(HorizontalLayout horizontalLayout, List<InputWordDto> wordsDtos){
        Card previousNextCard = null;

        for (int i = 0; i < wordsDtos.size(); i++){
            InputWordDto word = wordsDtos.get(i);
            Card actualCard;

            Runnable nextCardRunnable;
            if (i == 0) {
                Card nextCard = new Card(wordsDtos.get(i+1), CardType.QUESTION);
                actualCard = new Card(word, CardType.QUESTION);
                horizontalLayout.add(actualCard);

                nextCardRunnable = () -> {
                    UI.getCurrent().access(() -> {
                        horizontalLayout.remove(actualCard);
                        horizontalLayout.add(nextCard);
                        log.info("Change card ");
                        UI.getCurrent().push();
                    });
                };
                previousNextCard = nextCard;
            } else if (i == wordsDtos.size()-1) {
                actualCard = previousNextCard;

                nextCardRunnable = () -> {
                    UI.getCurrent().navigate("/stats");
                };


            } else {
                Card nextCard = new Card(wordsDtos.get(i+1), CardType.QUESTION);
                actualCard = previousNextCard;
                nextCardRunnable = () -> {
                    UI.getCurrent().access(()->{
                        horizontalLayout.remove(actualCard);
                        horizontalLayout.add(nextCard);
                        log.info("Change card 2");
                        UI.getCurrent().push();
                    });
                };
                previousNextCard = nextCard;
            }
            actualCard.setActionToCorrAnswBtn(nextCardRunnable);

        }
    }



}
