package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.SigninCredentialsDTO;
import com.leonardo.mangareader.models.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class ModelMapperService {
  
    private final PasswordEncoder passwordEncoder;

    public User SigninCredentialsToUser(SigninCredentialsDTO dto){
        return new User(
            null, 
            dto.getUsername(), 
            dto.getEmail(), 
            passwordEncoder.encode(dto.getPassword())
        );
    }

}
