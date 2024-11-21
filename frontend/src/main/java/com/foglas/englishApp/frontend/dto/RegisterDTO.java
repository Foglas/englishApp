package com.foglas.englishApp.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterDTO {
   private String nickname;
   private String email;
   private String password;
}
