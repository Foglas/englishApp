package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@Route("api/login")
public class LoginView extends MyAppLayout {
    @Autowired
    public LoginView(UserClient userClient, AuthenticationProvider authenticationProvider) {
        super(userClient, authenticationProvider);
    }
}
