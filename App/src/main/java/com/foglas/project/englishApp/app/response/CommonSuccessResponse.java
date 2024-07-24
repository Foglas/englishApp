package com.foglas.project.englishApp.app.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonSuccessResponse<T extends Object> implements CommonResponseInf<T>{

    private T clazz;

    @Override
    public T getResponse() {
        return clazz;
    }
}
