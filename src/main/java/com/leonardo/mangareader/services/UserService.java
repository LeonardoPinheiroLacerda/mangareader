package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.SigninCredentialsDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.repositories.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class UserService {
    
    private final UserRepository repository;
    private final ModelMapperService modelService;
    private final DtoMapperService dtoService;

    public UserDTO create(SigninCredentialsDTO dto){
        User user = modelService.SigninCredentialsToUser(dto);
        user = repository.save(user);
        return dtoService.UserToUserDTO(user);
    }

}
