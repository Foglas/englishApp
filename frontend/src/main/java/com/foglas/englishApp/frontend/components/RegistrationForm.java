package com.foglas.englishApp.frontend.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationForm extends FormLayout {

    private H3 title;
    private TextField nickName;
    private EmailField email;
    private PasswordField password;
    private PasswordField passwordConfirm;
    private Span errorMessageField;
    private Button submitButton;
    private Button loginButton;


    public RegistrationForm(){
        title = new H3("Signup form");
        nickName = new TextField("Nickname");
        email = new EmailField("Email");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        errorMessageField = new Span();

        submitButton = new Button("Complete registration");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton = new Button("Go to login",
                e-> navigateToLogin());
        loginButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        add(title, nickName, email, password,
                passwordConfirm, errorMessageField,
                submitButton, loginButton);

        // Max width of the Form
        setMaxWidth("500px");
        setWidth(90, Unit.PERCENTAGE);


        // Allow the form layout to be responsive.
        // On device widths 0-490px we have one column.
        // Otherwise, we have two columns.
        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    public void navigateToLogin(){
        UI.getCurrent().navigate("api/login");
    }
}
