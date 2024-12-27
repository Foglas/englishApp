package com.foglas.englishApp.frontend.dataProviders;

import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Scope("vaadin-session")
@Service
@AllArgsConstructor
@Getter
@Setter
public class CardDataProvider {
    private List<OutputWordDto> words;
    private final String exerciseKeyId = "exerciseId";


    public void saveExerciseId(Long exerciseId, VaadinSession session) {
        session.lock();
        session.setAttribute(exerciseKeyId, exerciseId);
        session.unlock();
    }

    public Long getExerciseId(VaadinSession session){
        session.lock();
        Object exerciseId = session.getAttribute(this.exerciseKeyId);
        session.unlock();

        if (exerciseId != null) {
            return (Long) exerciseId;
        } else {
            return null;
        }
    }
}
