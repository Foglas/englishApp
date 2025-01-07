package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.Card;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.foglas.englishApp.frontend.dto.PriorityDto;
import com.foglas.englishApp.frontend.enums.CardType;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "api/cards")
@Log4j2
@UIScope
public class CardView extends MyAppLayout {

    private final WordService wordService;
    private CardDataProvider cardData;
    private AuthenticationProvider authenticationProvider;
    private UI ui = UI.getCurrent();
    private VaadinSession session = VaadinSession.getCurrent();

    @Autowired
    public CardView(CardDataProvider cardData, UserService userService, AuthenticationProvider authenticationProvider, WordService wordService) {
        super(userService, authenticationProvider);
        this.authenticationProvider = authenticationProvider;
        this.cardData = cardData;
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        verticalLayout.add(horizontalLayout);
        setContent(verticalLayout);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        initCards(horizontalLayout, cardData.getWords());
        this.wordService = wordService;
    }

    private void initCards(HorizontalLayout horizontalLayout, List<OutputWordDto> wordsDtos) {
        Card previousNextCard = null;
        cardData.initSuccessAndFailed(session);

        for (int i = 0; i < wordsDtos.size(); i++) {
            OutputWordDto word = wordsDtos.get(i);
            Card actualCard;

            Runnable nextSuccessCardRunnable;
            Runnable nextFailureCardRunnable;
            if (i == 0 && wordsDtos.size() > 1) {
                Card nextCard = new Card(wordsDtos.get(i + 1), CardType.QUESTION);
                actualCard = new Card(word, CardType.QUESTION);
                horizontalLayout.add(actualCard);

                nextSuccessCardRunnable = createCommonCardHandler(PriorityType.SUCCESS, horizontalLayout, actualCard, nextCard, word);
                nextFailureCardRunnable = createCommonCardHandler(PriorityType.FAILURE, horizontalLayout, actualCard, nextCard, word);

                previousNextCard = nextCard;
            } else if (wordsDtos.size() == 1) {
                actualCard = new Card(word, CardType.QUESTION);
                horizontalLayout.add(actualCard);

                nextSuccessCardRunnable = createEndCardHandler(PriorityType.SUCCESS, word);
                nextFailureCardRunnable = createEndCardHandler(PriorityType.FAILURE, word);

            } else if (i == wordsDtos.size() - 1) {
                actualCard = previousNextCard;

                nextSuccessCardRunnable = createEndCardHandler(PriorityType.SUCCESS, word);
                nextFailureCardRunnable = createEndCardHandler(PriorityType.FAILURE, word);

            } else {
                Card nextCard = new Card(wordsDtos.get(i + 1), CardType.QUESTION);
                actualCard = previousNextCard;

                nextSuccessCardRunnable = createCommonCardHandler(PriorityType.SUCCESS, horizontalLayout, actualCard, nextCard, word);
                nextFailureCardRunnable = createCommonCardHandler(PriorityType.FAILURE, horizontalLayout, actualCard, nextCard, word);
                previousNextCard = nextCard;
            }
            actualCard.onCorrect(nextSuccessCardRunnable);
            actualCard.onIncorrect(nextFailureCardRunnable);
        }


    }

    private Runnable createCommonCardHandler(PriorityType type, HorizontalLayout horizontalLayout, Card actualCard, Card nextCard, OutputWordDto word) {
        return () -> {
            UI.getCurrent().access(() -> {
                horizontalLayout.remove(actualCard);
                horizontalLayout.add(nextCard);
                log.info("Change card 2");
                UI.getCurrent().push();
            });

            switch (type) {
                case PriorityType.SUCCESS ->
                        wordService.decreasePriority(new PriorityDto(word.getId(), cardData.getExerciseId(session)), authenticationProvider.getToken(session)).subscribe(
                                it -> {
                                    cardData.saveExerciseId(it.getId(), session);
                                    cardData.saveSuccess(session);
                                }
                        );

                case PriorityType.FAILURE ->
                        wordService.increasePriority(new PriorityDto(word.getId(), cardData.getExerciseId(session)), authenticationProvider.getToken(session)).subscribe(
                                it -> {
                                    cardData.saveExerciseId(it.getId(), session);
                                    cardData.saveFailed(session);
                                }
                        );
            }
        };
    }

    private Runnable createEndCardHandler(PriorityType type, OutputWordDto word) {
        return () -> {
            switch (type) {
                case PriorityType.SUCCESS -> {
                    wordService.decreasePriority(new PriorityDto(word.getId(), cardData.getExerciseId(session)), authenticationProvider.getToken(session)).subscribe(
                            (it) -> {
                                if (ui != null) {
                                    session.lock();
                                    ui.navigate("/stats");
                                    session.unlock();
                                }
                            }
                    );
                    cardData.saveSuccess(session);
                }

                case PriorityType.FAILURE -> {
                    wordService.increasePriority(new PriorityDto(word.getId(), cardData.getExerciseId(session)), authenticationProvider.getToken(session)).subscribe(
                            (it) -> {
                                if (ui != null) {
                                    session.lock();
                                    ui.navigate("/stats");
                                    session.unlock();
                                }
                            }
                    );
                    cardData.saveFailed(session);
                }
            }

            cardData.saveExerciseId(null, session);
            UI.getCurrent().navigate("/stats");
        };
    }

    enum PriorityType {
        FAILURE, SUCCESS
    }
}
