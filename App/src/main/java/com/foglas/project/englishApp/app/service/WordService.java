package com.foglas.project.englishApp.app.service;

import com.foglas.project.englishApp.app.dto.InputWordDto;
import com.foglas.project.englishApp.app.dto.ModelMapper.AppModelMapper;
import com.foglas.project.englishApp.app.repository.ExampleRepo;
import com.foglas.project.englishApp.app.repository.Interfaces.ExampleRepoInf;
import com.foglas.project.englishApp.app.repository.Interfaces.WordRepoInf;
import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.project.englishApp.app.repository.WordRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class WordService implements WordServiceInf{
    private ExampleRepoInf exampleRepo;
    private WordRepoInf wordRepo;
    private ModelMapper modelMapper;

    @Autowired
    public WordService(ExampleRepo exampleRepoImpl, WordRepo wordRepoImpl, AppModelMapper modelMapper){
        this.exampleRepo = exampleRepoImpl;
        this.wordRepo = wordRepoImpl;
        this.modelMapper = modelMapper.getModelMapper();
    }

    @Override
    public ResponseEntity<?> createWord(Optional<InputWordDto> word) {
        log.info("word " + word.get().getText());
        Word wordToSave = this.modelMapper.map(word, Word.class);
        log.info(wordToSave);
        return ResponseEntity.ok(word.get());
    }

    @Override
    public ResponseEntity<?> deleteWord(long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateWord(Optional<Word> word) {
        return null;
    }
}
