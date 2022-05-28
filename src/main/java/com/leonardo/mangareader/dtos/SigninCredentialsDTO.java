package com.leonardo.mangareader.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@ToString
public class SigninCredentialsDTO {
    
    private String username;
    private String password;
    private String passwordConfirmation;
    private String email;

}
