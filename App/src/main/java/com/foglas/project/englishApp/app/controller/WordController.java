package com.foglas.project.englishApp.app.controller;

import com.foglas.project.englishApp.app.dto.InputWordDto;
import com.foglas.project.englishApp.app.service.WordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("${englishApp.api.requestPath}")
public class WordController {
    private WordService wordService;

    @Autowired
    public WordController(WordService wordService){
        this.wordService = wordService;
    }

    @PostMapping("/createWord")
    public ResponseEntity<?> createWord(@RequestBody InputWordDto word){
        Optional<InputWordDto> wordOpt = Optional.of(word);
        return wordService.createWord(wordOpt);
    }
}
