package com.leonardo.mangareader.dtos;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class SigninCredentialsDTO {

    @NotBlank(message = "Esse campo é obrigatório.")
    private String username;

    @NotBlank(message = "Esse campo é obrigatório.")
    private String password;
    private String passwordConfirmation;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "Esse campo é obrigatório.")
    private String email;

    @AssertTrue(message = "As senhas não conferem.")
    public boolean isPasswordConfirmation(){
        if(password == null){
            return true;
        }
        return password.equals(passwordConfirmation);
    }

}
