package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.dto.ExampleDto;
import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.components.Card;
import com.foglas.englishApp.frontend.components.CardType;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.Transport;

import java.util.ArrayList;
import java.util.List;

@Route(value = "api/cards")
public class CardView extends MyAppLayout {


    public CardView(){
        List<ExampleDto> examples = new ArrayList<>();
        examples.add(new ExampleDto("Can i buy it"));
        examples.add(new ExampleDto("I bought that one year ago"));
        InputWordDto test = InputWordDto.builder().text("buy")
                .countable("C")
                .secondForm("bought")
                .thirdForm("bought")
                .examples(examples)
                .build();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidthFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.add(new Card(test, CardType.QUESTION));
        verticalLayout.add(horizontalLayout);
        setContent(verticalLayout);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    private void setWords(){

    }

}
