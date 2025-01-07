package com.foglas.englishApp.frontend.dataProviders;

import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.foglas.englishApp.frontend.dto.ResultDto;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    private final String exerciseResultKeyId = "exerciseResultId";
    private final String exerciseSuccessKeyId = "exerciseSuccessKeyId";
    private final String exerciseFailKeyId = "exerciseFailKeyId";
    private final String exerciseWordsCountKey = "exerciseWordsCountKey";
    private final String actualWordKey = "actualWord";

    public void saveExerciseId(Long exerciseId, VaadinSession session) {
        session.lock();
        session.setAttribute(exerciseKeyId, exerciseId);
        session.unlock();
        if (exerciseId != null) {
            saveExerciseIdForResult(exerciseId, session);
        }
    }

    public void saveExerciseIdForResult(Long exerciseId, VaadinSession session) {
        session.lock();
        session.setAttribute(exerciseResultKeyId, exerciseId);
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

    public void saveSuccess(VaadinSession session){
        session.lock();
        Integer previous = (Integer) session.getAttribute(exerciseSuccessKeyId);
        session.setAttribute(exerciseSuccessKeyId, ++previous);
        session.unlock();
    }

    public void saveFailed(VaadinSession session){
        session.lock();
        Integer previous = (Integer) session.getAttribute(exerciseFailKeyId);
        session.setAttribute(exerciseFailKeyId, ++previous);
        session.unlock();
    }

    public void saveActualWord(OutputWordDto outputWordDto, VaadinSession session){
        session.lock();
        session.setAttribute(actualWordKey, outputWordDto);
        session.unlock();
    }

    public OutputWordDto getActualWord(VaadinSession session){
        session.lock();
        OutputWordDto outputWordDto = (OutputWordDto) session.getAttribute(actualWordKey);
        session.unlock();
        return outputWordDto;
    }

    public void saveWordsCount(Integer count, VaadinSession session){
        session.lock();
        session.setAttribute(exerciseWordsCountKey, count);
        session.unlock();
    }

    public void initSuccessAndFailed(VaadinSession session){
        session.lock();
        session.setAttribute(exerciseSuccessKeyId, 0);
        session.setAttribute(exerciseFailKeyId, 0);
        session.unlock();
    }

    public ResultDto getResults(VaadinSession session){
        session.lock();
        Integer success = (Integer) session.getAttribute(exerciseSuccessKeyId);
        Integer failed = (Integer) session.getAttribute(exerciseFailKeyId);
        session.unlock();
        return new ResultDto(success, failed);
    }



    public Long getExerciseResultId(VaadinSession session){
        session.lock();
        Object exerciseId = session.getAttribute(this.exerciseResultKeyId);
        session.unlock();

        if (exerciseId != null) {
            return (Long) exerciseId;
        } else {
            return null;
        }
    }
}
