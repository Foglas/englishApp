package com.foglas.englishApp.frontend.dataProviders;

import com.foglas.englishApp.dto.InputWordDto;
import com.vaadin.flow.spring.annotation.RouteScope;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@UIScope
@Component
@AllArgsConstructor
@Getter
@Setter
public class CardDataProvider {

    private List<InputWordDto> words;


}
