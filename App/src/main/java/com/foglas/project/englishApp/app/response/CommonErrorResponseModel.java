package com.foglas.project.englishApp.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.el.EvaluationListener;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonErrorResponseModel implements CommonResponseInf<Map<String,String>>{

   private final Map<String, String> validationViolationMap;

    public CommonErrorResponseModel(List<FieldError> fieldErrors){
       validationViolationMap = transformErrorListIntoReponseMap(fieldErrors);
    }

    private Map<String, String> transformErrorListIntoReponseMap(List<FieldError> fieldErrors){
        Map<String, String> responseMap = new HashMap<>();
        fieldErrors.stream().forEach((fieldErrorsInner) -> {responseMap.put(fieldErrorsInner.getField(), fieldErrorsInner.getDefaultMessage());});
        return responseMap;
    }


    @Override
    public Map<String, String> getResponse() {
        return validationViolationMap;
    }
}
