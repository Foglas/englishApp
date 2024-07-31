package com.foglas.project.englishApp.app.modelMapper;

import com.foglas.project.englishApp.app.domain.Example;
import com.foglas.project.englishApp.app.domain.Word;
import com.foglas.englishApp.dto.ExampleDto;

import com.foglas.project.englishApp.app.modelMapper.inf.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class ExampleMapper implements BaseMapper<ExampleDto, Example> {

    @Override
    @Mapping(source = "text", target = "text")
    abstract public ExampleDto toDTO(Example entity);
    @Named("ExampleDTOtoExampleWithWord")
    public Example addWordInfo(Example example, Word word) {
        return new Example(example.getText(),word);
    }


}