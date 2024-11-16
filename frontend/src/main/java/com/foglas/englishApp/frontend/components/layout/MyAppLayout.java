package com.foglas.englishApp.frontend.components.layout;

import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.foglas.englishApp.frontend.endpoins.UserClientInf;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MyAppLayout extends AppLayout{

  private VerticalLayout mainLayout = new VerticalLayout();
  private UserClientInf userClient = new UserClient();

  public MyAppLayout(){
      if (AuthenticationProvider.isLoggedIn){
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
          HorizontalLayout horizontalNavigation = getNavigation(true);
          addToNavbar(horizontalNavigation);
      }

  }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.getStyle().set("margin-top","2em");
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
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setOpened(shouldOpenOverlay);
        loginOverlay.setForgotPasswordButtonVisible(false);
        loginOverlay.addLoginListener(this::loginHandler);
        navigation.add(loginOverlay);

        //loginButton
        Button login;
        if (AuthenticationProvider.isLoggedIn){
            login = new Button("Logout");
            login.addClickListener((e)-> {
                logoutHandler();
                login.setText("Login");
            });
        } else {
            login = new Button("Login");
            login.addClickListener((e)-> {
                log.info("Logged in");
                loginOverlay.setOpened(true);
            });
        }
        navigation.add(login);

        return navigation;
    }


    public void loginHandler(AbstractLogin.LoginEvent event){
      userClient.login();
    }

    public void logoutHandler(){
      userClient.logout();
      log.info("Logged out");
    }
}
