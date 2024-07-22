package com.foglas.project.englishApp.app.service;

import com.foglas.project.englishApp.app.dto.InputWordDto;
import com.foglas.project.englishApp.app.dto.ModelMapper.WordMapper;
import com.foglas.project.englishApp.app.repository.ExampleRepo;
import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.project.englishApp.app.repository.WordRepo;
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
    public ResponseEntity<?> createWord(Optional<InputWordDto> word) {
        log.info("word " + word.get().getText());
        Word wordToSave = this.modelMapper.toEntityWithFillExampleWithWord(word.get());
        //  log.info(wordToSave);
        Word wordFromDb = wordRepo.save(wordToSave);
        Optional<Word> getWord = wordRepo.findById(wordFromDb.getId());
        return ResponseEntity.ok(modelMapper.toDTO(getWord.get()));
    }

    @Override
    public ResponseEntity<?> deleteWord(long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateWord(Optional<Word> word) {
        return null;
    }

    @Override
    public ResponseEntity<Word> getWordById(long id) {
        return null;
    }
}
