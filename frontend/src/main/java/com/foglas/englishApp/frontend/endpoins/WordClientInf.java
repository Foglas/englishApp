package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.frontend.dto.ExerciseDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.foglas.englishApp.frontend.dto.PriorityDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WordClientInf {

    Mono<String> sendSave(InputWordDto inputWordDto, String token);
    Mono<String> sendUpdate(OutputWordDto word, String token);
    List<OutputWordDto> getWordSet(int count, String token, Long userId);

    Mono<ExerciseDto> increasePriority(PriorityDto priorityDto, String token);
    Mono<ExerciseDto> decreasePriority(PriorityDto priorityDto, String token);

}
