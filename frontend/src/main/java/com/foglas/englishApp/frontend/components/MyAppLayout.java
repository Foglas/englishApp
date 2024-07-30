package com.foglas.englishApp.frontend.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MyAppLayout extends AppLayout{

  public MyAppLayout(){
      DrawerToggle toggle = new DrawerToggle();

      H1 title = new H1("EnglishApp");
      title.getStyle().set("font-size", "var(--lumo-font-size-l)")
              .set("margin", "0");

      SideNav nav = getSideNav();

      Scroller scroller = new Scroller(nav);
      scroller.setClassName(LumoUtility.Padding.SMALL);

      setContent(new VerticalLayout());
      addToDrawer(scroller);
      addToNavbar(toggle, title);
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
}
