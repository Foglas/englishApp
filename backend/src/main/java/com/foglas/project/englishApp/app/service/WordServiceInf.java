package com.foglas.project.englishApp.app.service;

import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.project.englishApp.app.response.inf.CommonResponseInf;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface WordServiceInf {

    ResponseEntity<CommonResponseInf> createWord(Optional<InputWordDto> word);
    ResponseEntity<CommonResponseInf> deleteWord(long id);
    ResponseEntity<CommonResponseInf> updateWord(Optional<Word> word);

    ResponseEntity<CommonResponseInf> getWordById(long id);

}
