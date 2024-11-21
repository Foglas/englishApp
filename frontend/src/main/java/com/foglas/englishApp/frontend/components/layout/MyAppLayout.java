package com.foglas.englishApp.frontend.components.layout;

import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dto.LoginDTO;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.foglas.englishApp.frontend.endpoins.UserClientInf;
import com.foglas.englishApp.frontend.views.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;


@UIScope
@Slf4j
public class MyAppLayout extends AppLayout {

    private VerticalLayout mainLayout = new VerticalLayout();
    private UserClientInf userClient;
    private LoginOverlay loginOverlay;
    private UI ui = UI.getCurrent();
    private AuthenticationProvider authenticationProvider;
    protected VaadinSession session = VaadinSession.getCurrent();

    @Autowired
    public MyAppLayout(UserClient userClient, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.userClient = userClient;
        LoginI18n login = LoginI18n.createDefault();
        login.getForm().setUsername("Email");
        loginOverlay = new LoginOverlay(login);


        if (AuthenticationProvider.isLoggedIn) {
            DrawerToggle toggle = new DrawerToggle();

            H1 title = new H1("EnglishApp");
            title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                    .set("margin", "0");

            SideNav nav = getSideNav();

            Scroller scroller = new Scroller(nav);
            scroller.setClassName(LumoUtility.Padding.SMALL);

            setContent(mainLayout);
            addToDrawer(scroller);
            addToNavbar(toggle, title);

            HorizontalLayout horizontalNavigation = getNavigation(false);
            addToNavbar(horizontalNavigation);
        } else {
            addRegisterButton();

            HorizontalLayout horizontalNavigation = getNavigation(true);
            addToNavbar(horizontalNavigation);
        }

    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.getStyle().set("margin-top", "2em");
        sideNav.addItem(
                new SideNavItem("Dashboard", "/dashboard",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Practise", "/practise", VaadinIcon.BOOK.create()),
                new SideNavItem("Word management", "/api/word/add", VaadinIcon.PLUS_MINUS.create()));
        return sideNav;
    }

    private HorizontalLayout getNavigation(boolean shouldOpenOverlay) {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL, LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL);
        navigation.setJustifyContentMode(HorizontalLayout.JustifyContentMode.END);

        //loginForm
        loginOverlay.setOpened(shouldOpenOverlay);
        loginOverlay.setForgotPasswordButtonVisible(false);
        loginOverlay.addLoginListener(this::loginHandler);

        navigation.add(loginOverlay);

        //loginButton
        Button login = new Button("Logout");
        login.addClickListener((e) -> {
            logoutHandler();
            login.setText("Login");
        });
        navigation.add(login);
        return navigation;
    }


    public void loginHandler(AbstractLogin.LoginEvent event) {
        Mono<String> response = userClient.login(new LoginDTO(event.getUsername(), event.getPassword()));
        response.subscribe(it->{
                if (it != null){
                    session.lock();
                    loginOverlay.close();
                    session.unlock();
                    authenticationProvider.storeToken(it, session);
                    ui.access(() -> {ui.navigate("/practise");
                    ui.refreshCurrentRoute(false);});
                } else {
                    loginOverlay.setError(true);
                }
        },
                error -> {
                    ui.access(() -> loginOverlay.setError(true)); // Re-enable button and show error
                }
        );

    }

    public void logoutHandler() {
        userClient.logout();
        log.info("Logged out");
    }

    private void addRegisterButton() {
        Button registerButton = new Button("Go to Registration",
                e -> navigateToRegistration());
        registerButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        loginOverlay.getFooter().add(registerButton);
    }

    private void navigateToRegistration() {
        loginOverlay.close();
        UI.getCurrent().navigate("api/registration");
    }
}
