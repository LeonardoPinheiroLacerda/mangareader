package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.SigninCredentialsDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.exceptions.DataIntegrityException;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
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
        try{
            user = repository.save(user);
        }catch(Exception e){
            throw new DataIntegrityException("Esse objeto não pode ser persistido", e);
        }
        return dtoService.userToUserDTO(user);
    }

    public UserDTO findByUsername(String username){
        User user = repository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("Nenhum usuário esta cadastrado com esse nome de usuário."));
        return dtoService.userToUserDTO(user);
    }

    public UserDTO findByEmail(String email){
        User user = repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Nenhum usuário está cadastrado com esse e-mail."));
        return dtoService.userToUserDTO(user);
    }

}
