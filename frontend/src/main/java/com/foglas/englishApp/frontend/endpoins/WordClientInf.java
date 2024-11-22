package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.dto.InputWordDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WordClientInf {

    Mono<String> sendSave(InputWordDto inputWordDto, String token);

    List<InputWordDto> getWordSet(int count, String token);

}
