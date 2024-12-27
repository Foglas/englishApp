package com.foglas.englishApp.frontend.Service;

import com.foglas.englishApp.frontend.dto.ExerciseDto;
import com.foglas.englishApp.frontend.dto.InputWordDto;
import com.foglas.englishApp.frontend.dto.OutputWordDto;
import com.foglas.englishApp.frontend.dto.PriorityDto;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WordService {

    private WordClient wordClient;

    public WordService(WordClient wordClient){
        this.wordClient = wordClient;
    }

    public Mono<String> saveWord(InputWordDto wordDto, String token){
        return wordClient.sendSave(wordDto, token);
    }

    public List<OutputWordDto> getWords(Integer count, String token, Long userId){
       return wordClient.getWordSet(count, token, userId);
    }

    public Mono<ExerciseDto> increasePriority(PriorityDto priorityDto, String token){
        return wordClient.increasePriority(priorityDto, token);
    }

    public Mono<ExerciseDto> decreasePriority(PriorityDto priorityDto, String token){
        return wordClient.decreasePriority(priorityDto, token);
    }
}
