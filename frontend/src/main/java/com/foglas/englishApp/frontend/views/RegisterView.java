package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.RegistrationForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@Route(value = "api/registration")
public class RegisterView extends VerticalLayout {
    private RegistrationForm registration;


    @Autowired
    public RegisterView(RegistrationForm registrationForm) {
        this.registration = registrationForm;
        initForm(registrationForm);
    }

    public void initForm(RegistrationForm registration) {
        registration.getStyle().set("margin-top", "2em");
        HorizontalLayout horizontalWrapper = new HorizontalLayout();
        horizontalWrapper.setSizeFull();
        horizontalWrapper.setAlignItems(Alignment.CENTER);
        horizontalWrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        horizontalWrapper.add(registration);
        add(horizontalWrapper);
    }
}

