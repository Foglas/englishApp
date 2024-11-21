package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.dto.RegisterDTO;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;


@UIScope
@Component
public class RegistrationForm extends FormLayout {

    private UserClient userClient;
    private UI ui = UI.getCurrent();

    @Autowired
    public RegistrationForm(UserClient userClient) {
        this.userClient = userClient;
        init();
    }

    private H3 title;
    private TextField nickName;
    private EmailField email;
    private PasswordField password;
    private PasswordField passwordConfirm;
    private Span errorMessageField;
    private Button submitButton;
    private Button loginButton;


    public void init() {
        title = new H3("Signup form");
        nickName = new TextField("Nickname");
        email = new EmailField("Email");

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        errorMessageField = new Span();

        submitButton = new Button("Complete registration");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(this::registrationHandler);
        loginButton = new Button("Go to login", e -> navigateToLogin());
        loginButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        add(title, nickName, email, password, passwordConfirm, errorMessageField, submitButton, loginButton);

        setMaxWidth("500px");
        setWidth(90, Unit.PERCENTAGE);

        setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    public void registrationHandler(ClickEvent event) {
        Mono<String> response = userClient.register(new RegisterDTO(nickName.getValue(), email.getValue(), password.getValue()));
        response.subscribe(it -> {
                    if (it != null) {
                        ui.access(() -> ui.navigate("/practise"));
                    }
                },
                error -> {
                    ui.access(() -> Notification.show(error.getMessage().split("message\":\"")[1].replace("\"}", ""), 100000, Notification.Position.BOTTOM_CENTER));   }
        );
    }

    public void navigateToLogin() {
        UI.getCurrent().navigate("api/login");
    }
}
