package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.components.enums.Strategy;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PractiseForm extends VerticalLayout {

    public PractiseForm(){
        setWidthFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        ComboBox<Strategy> strategies = new ComboBox("Strategy of finding words");
        strategies.setItems(Strategy.values());
        strategies.setItemLabelGenerator((strategy)-> strategy.name());
        add(strategies);
    }
}
