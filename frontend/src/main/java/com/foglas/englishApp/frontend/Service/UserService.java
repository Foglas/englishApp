package com.foglas.englishApp.frontend.Service;

import com.foglas.englishApp.frontend.dto.InputUserDto;
import com.foglas.englishApp.frontend.dto.LoginDTO;
import com.foglas.englishApp.frontend.dto.RegisterDTO;
import com.foglas.englishApp.frontend.endpoins.UserClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserClient userClient;

    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public Mono<String> register(RegisterDTO registerDTO) {
        return userClient.register(registerDTO);
    }

    public Mono<InputUserDto> login(LoginDTO loginDTO) {
       return userClient.login(loginDTO);
    }

    public void logout(){
        userClient.logout();
    }
}
