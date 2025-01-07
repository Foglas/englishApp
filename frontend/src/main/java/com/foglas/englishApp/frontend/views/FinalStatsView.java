package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.ResultService;
import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dataProviders.CardDataProvider;
import com.foglas.englishApp.frontend.domain.Stats;
import com.foglas.englishApp.frontend.dto.ResultDto;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.apache.coyote.http11.Http11InputBuffer;
import org.springframework.beans.factory.annotation.Autowired;

@Route("stats")
public class FinalStatsView extends MyAppLayout {

    private Stats stats;
    private VaadinSession session = VaadinSession.getCurrent();

    @Autowired
    public FinalStatsView(UserService userService, ResultService resultService, CardDataProvider cardDataProvider, AuthenticationProvider authenticationProvider){
        super(userService, authenticationProvider);
        HorizontalLayout horizontalWrapper = new HorizontalLayout();
        horizontalWrapper.setWidthFull();
        horizontalWrapper.setHeightFull();
        horizontalWrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Div content = new Div();
        content.setWidth(60f, Unit.PERCENTAGE);
        content.setHeight(80f, Unit.PERCENTAGE);

        H1 header = new H1("Final stats");
        header.setWidth(40f, Unit.PERCENTAGE);
        header.getStyle().set("background-color", "rgba(28, 116, 255)");
        header.getStyle().set("padding", "0.5em 1.6em 0.5em 0.8em");
        header.getStyle().set("border-radius", "0em 1em 1em 0em");
        header.getStyle().set("margin-top", "1em");
        header.getStyle().set("font-size", "1.8em");
        content.add(header);
        content.getStyle().set("background-color", "hsla(214, 61%, 25%, 0.05)");
        content.getStyle().set("margin-top", "2em");
        content.getStyle().set("border-radius", "1em");
        ResultDto result = cardDataProvider.getResults(session);

        H2 headerSuccess = new H2("Success count");
        Paragraph success = new Paragraph(result.getSuccess().toString());
        H2 headerFailure = new H2("Failure count");
        Paragraph failure = new Paragraph(result.getFailed().toString());
        content.add(headerSuccess, success, headerFailure, failure);

        horizontalWrapper.add(content);
        setContent(horizontalWrapper);


    }



}
