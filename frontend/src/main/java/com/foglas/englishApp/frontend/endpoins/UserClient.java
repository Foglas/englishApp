package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.vaadin.flow.component.UI;
import org.springframework.stereotype.Component;

@Component
public class UserClient implements UserClientInf{

    @Override
    public void login() {

    }

    @Override
    public void logout() {
        AuthenticationProvider.isLoggedIn = false;
        UI.getCurrent().push();
    }

    @Override
    public void register() {

    }
}
