package com.foglas.project.englishApp.app.repository;

import com.foglas.project.englishApp.app.domain.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepo extends CrudRepository<Word, Long> {

    Word save(Word word);
}
