package com.foglas.englishApp.frontend.dataProviders;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("vaadin-session")
@Service
public class AuthenticationProvider {
    private final String tokenKey = "Bearer";

    public void storeToken(String token, VaadinSession session) {
        session.lock();
        session.setAttribute(tokenKey, token);
        session.unlock();
    }

    public String getToken(VaadinSession session) {
        session.lock();
        Object token = session.getAttribute(tokenKey);
        session.unlock();
        if (token != null) {
            return (String) token;
        } else {
            return null;
        }
    }

    public boolean isLoggedIn(VaadinSession session) {
        if (session == null) {
            return false;
        } else if (getToken(session) == null) {
           return false;
        } else {
            return true;
        }
    }

    public void logout(VaadinSession session) {
        session.setAttribute(tokenKey, null);
    }
}
