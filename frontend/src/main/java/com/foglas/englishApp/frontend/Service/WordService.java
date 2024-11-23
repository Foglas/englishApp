package com.foglas.englishApp.frontend.Service;

import com.foglas.englishApp.frontend.dto.InputWordDto;
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

    public List<InputWordDto> getWords(Integer count, String token){
       return wordClient.getWordSet(count, token);
    }
}
