package com.foglas.englishApp.frontend.Service;

import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.englishApp.frontend.endpoins.WordClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    private WordClient wordClient;

    public WordService(WordClient wordClient){
        this.wordClient = wordClient;
    }

    public void saveWord(InputWordDto wordDto, String token){
        wordClient.sendSave(wordDto, token);
    }

    public List<InputWordDto> getWords(Integer count, String token){
       return wordClient.getWordSet(count, token);
    }
}
