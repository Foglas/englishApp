package com.foglas.project.englishApp.app.service;

import com.foglas.englishApp.dto.InputWordDto;
import com.foglas.project.englishApp.app.modelMapper.WordMapper;
import com.foglas.project.englishApp.app.repository.ExampleRepo;
import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.project.englishApp.app.repository.WordRepo;
import com.foglas.project.englishApp.app.response.inf.CommonResponseInf;
import com.foglas.project.englishApp.app.response.CommonSuccessResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class WordService implements WordServiceInf{
    private ExampleRepo exampleRepo;
    private WordRepo wordRepo;
    private WordMapper modelMapper;

    @Autowired
    public WordService(ExampleRepo exampleRepoImpl, WordRepo wordRepoImpl, WordMapper modelMapper){
        this.exampleRepo = exampleRepoImpl;
        this.wordRepo = wordRepoImpl;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CommonResponseInf> createWord(Optional<InputWordDto> word) {
        log.info("word " + word.get().getText());
        Word wordToSave = this.modelMapper.toEntityWithFillExampleWithWord(word.get());
        Word wordFromDb = wordRepo.save(wordToSave);
        Optional<Word> getWord = wordRepo.findById(wordFromDb.getId());
        return ResponseEntity.ok(new CommonSuccessResponse<>(modelMapper.toDTO(getWord.get())));
    }

    @Override
    public ResponseEntity<CommonResponseInf> deleteWord(long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponseInf> updateWord(Optional<Word> word) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponseInf> getWordById(long id) {
        return null;
    }
}
