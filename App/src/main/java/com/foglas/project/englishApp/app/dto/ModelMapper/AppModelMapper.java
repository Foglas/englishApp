package com.foglas.project.englishApp.app.dto.ModelMapper;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Getter
@Log4j2
public class AppModelMapper {

    ModelMapper modelMapper;

    public AppModelMapper(){
        modelMapper = createModelMapper();
        log.info("hello mapper");
    }

    @Bean
    private ModelMapper createModelMapper(){
        return new ModelMapper();
    }


}
