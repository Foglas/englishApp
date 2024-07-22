package com.foglas.project.englishApp.app.service;

import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.project.englishApp.app.dto.InputWordDto;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface WordServiceInf {

    ResponseEntity<?> createWord(Optional<InputWordDto> word);
    ResponseEntity<?> deleteWord(long id);
    ResponseEntity<?> updateWord(Optional<Word> word);

    ResponseEntity<Word> getWordById(long id);

}
