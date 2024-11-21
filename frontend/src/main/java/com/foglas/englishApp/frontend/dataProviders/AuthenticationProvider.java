package com.foglas.englishApp.frontend.dataProviders;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationProvider {
    public static boolean isLoggedIn = false;
    private final String tokenKey = "Bearer";
    private VaadinSession session = VaadinSession.getCurrent();

    public void storeToken(String token, VaadinSession session) {
        isLoggedIn = true;
        session.lock();
        session.setAttribute(tokenKey, token);
        session.unlock();
    }

    public String getToken(VaadinSession session) {
        session.lock();
        String token = session.getAttribute(tokenKey).toString();
        session.unlock();
        return token;
    }

    public void logout() {
        isLoggedIn = false;
        VaadinSession.getCurrent().setAttribute(tokenKey, null);
    }
}
