package com.foglas.englishApp.frontend.views;

import com.foglas.englishApp.frontend.Service.UserService;
import com.foglas.englishApp.frontend.Service.WordService;
import com.foglas.englishApp.frontend.components.ResultComponent;
import com.foglas.englishApp.frontend.components.WordManagementComponent;
import com.foglas.englishApp.frontend.components.layout.MyAppLayout;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

@Route("api/result")
public class ResultView extends MyAppLayout {


    public ResultView(ResultComponent resultComponent, UserService userService, AuthenticationProvider authenticationProvider) {
        super(userService, authenticationProvider);

        VerticalLayout wrapper = new VerticalLayout();

        wrapper.setSizeFull();
        wrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        wrapper.add(resultComponent);
        setContent(wrapper);
    }
}
