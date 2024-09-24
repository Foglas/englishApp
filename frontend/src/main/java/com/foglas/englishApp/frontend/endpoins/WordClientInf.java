package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.dto.InputWordDto;

import java.util.List;

public interface WordClientInf {

    void sendSave(InputWordDto inputWordDto);

    List<InputWordDto> getWordSet(int count);

}
