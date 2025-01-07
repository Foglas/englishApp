package com.foglas.englishApp.frontend.Service;

import com.foglas.englishApp.frontend.dto.ResultDto;
import com.foglas.englishApp.frontend.endpoins.ResultClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private ResultClient resultClient;
    @Autowired
    public ResultService(ResultClient resultClient) {
        this.resultClient = resultClient;
    }

    public List<ResultDto> getAllResultsByUserId(Long userId, String token){
       return resultClient.getAllResultsByUserId(userId, token);
    }
}
