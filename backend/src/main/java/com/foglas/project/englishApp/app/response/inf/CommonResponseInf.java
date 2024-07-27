package com.foglas.project.englishApp.app.response.inf;

import com.fasterxml.jackson.annotation.JsonCreator;

public interface CommonResponseInf<T> {

    @JsonCreator
    T getResponse();

}
