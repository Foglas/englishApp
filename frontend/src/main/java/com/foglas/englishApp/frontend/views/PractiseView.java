package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.PractiseForm;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/practise")
public class PractiseView extends MyAppLayout {

    public PractiseView(){
        setContent(new PractiseForm());
    }


}
