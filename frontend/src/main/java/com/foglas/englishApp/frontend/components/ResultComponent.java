package com.foglas.englishApp.frontend.components;

import com.foglas.englishApp.frontend.Service.ResultService;
import com.foglas.englishApp.frontend.dataProviders.AuthenticationProvider;
import com.foglas.englishApp.frontend.dto.ResultDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UIScope
@org.springframework.stereotype.Component
@Log4j2
public class ResultComponent extends Div implements AfterNavigationObserver {

    private final ResultService resultService;
    private final AuthenticationProvider authenticationProvider;
    private VaadinSession session = VaadinSession.getCurrent();
    private List<ResultDto> results;
    private Grid<ResultDto> grid;

    @Autowired
    public ResultComponent(ResultService resultService, AuthenticationProvider authenticationProvider) {
        setWidth(70, Unit.PERCENTAGE);
        setHeight(80, Unit.PERCENTAGE);

        this.resultService = resultService;
        this.authenticationProvider = authenticationProvider;

        }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if (grid == null){
            grid = new Grid<>(ResultDto.class, false);
            grid.addColumn(ResultDto::getSuccess).setHeader("Success");
            grid.addColumn(ResultDto::getFailed).setHeader("Failed");
        }
        results = resultService.getAllResultsByUserId(authenticationProvider.getUserId(session), authenticationProvider.getToken(session));
        grid.setItems(results);

        add(grid);
    }
}


