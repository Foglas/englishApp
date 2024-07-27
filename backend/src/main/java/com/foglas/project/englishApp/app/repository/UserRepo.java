package com.foglas.project.englishApp.app.repository;

import com.foglas.project.englishApp.app.domain.UserDetail;
import com.foglas.project.englishApp.app.domain.Word;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserDetail, Long> {
}
