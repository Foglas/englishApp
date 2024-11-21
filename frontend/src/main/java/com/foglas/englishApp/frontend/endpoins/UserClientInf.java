package com.foglas.englishApp.frontend.endpoins;

import com.foglas.englishApp.frontend.dto.LoginDTO;
import com.foglas.englishApp.frontend.dto.RegisterDTO;
import reactor.core.publisher.Mono;

public interface UserClientInf {

    Mono<String> login(LoginDTO loginDTO);
    void logout();
    Mono<String> register(RegisterDTO registerDTO);
}

