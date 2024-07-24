package com.foglas.project.englishApp.app.dto.ModelMapper;

import com.foglas.project.englishApp.app.domain.Example;
import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.project.englishApp.app.dto.InputWordDto;
import com.foglas.project.englishApp.app.dto.ModelMapper.inf.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = ExampleMapper.class)
@Component
public abstract class WordMapper implements BaseMapper<InputWordDto, Word> {

    @Autowired
    private ExampleMapper exampleMapper;

    @Override
    abstract public Word toEntity(InputWordDto dto);

    public Word toEntityWithInnerExample(InputWordDto dto) {
        Word word = toEntity(dto);
        word.getExamples().stream().map((actualExample) -> { return Example.builder().word(actualExample.getWord());}).collect(Collectors.toList());
        return word;
    }

    public Word toEntityWithFillExampleWithWord(InputWordDto dto){
       Word wordFromMapping = this.toEntity(dto);
       List<Example> examples = wordFromMapping.getExamples();
       final Word word = Word.builderWordSeparateExamples(wordFromMapping, examples).build();
        for (int i = 0; i<examples.size(); i++) {
          Example example = examples.get(i);
          example = exampleMapper.addWordInfo(example, word);
          examples.set(i, example);
        }

       return word;
    }
}

