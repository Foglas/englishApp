package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.dto.InputWordDto;

import java.util.List;

public interface WordClientInf {

    void sendSave(InputWordDto inputWordDto, String token);

    List<InputWordDto> getWordSet(int count, String token);

}
