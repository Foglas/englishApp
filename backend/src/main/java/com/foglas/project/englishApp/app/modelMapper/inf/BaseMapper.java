package com.foglas.project.englishApp.app.modelMapper.inf;

import com.foglas.project.englishApp.app.domain.Example;
import org.mapstruct.Named;

public interface BaseMapper<D,E> {

    D toDTO(E entity);

    E toEntity(D dto);

    @Named("setListToDefault")
    default E setValueToDefault(D dto){
        return null;
    }
}