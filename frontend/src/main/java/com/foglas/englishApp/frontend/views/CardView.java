package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.Card;
import com.foglas.englishApp.frontend.components.MyAppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "api/cards")
public class CardView extends MyAppLayout {


    public CardView(){
    VerticalLayout verticalLayout = new VerticalLayout();
    verticalLayout.setWidthFull();
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    horizontalLayout.setWidthFull();
    horizontalLayout.add(new Card("hello", "ahoj"));
    verticalLayout.add(horizontalLayout);
    setContent(verticalLayout);
    horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

}
