package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.SigninCredentialsDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.exceptions.DataIntegrityException;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.repositories.UserRepository;
import com.leonardo.mangareader.security.AppUserDetails;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class UserService {
    
    private final UserRepository repository;
    private final DtoMapperService dtoService;
    private final PasswordEncoder passwordEncoder;

    public UserDTO create(SigninCredentialsDTO dto){
        User user = new User(
            null, 
            dto.getUsername(), 
            dto.getEmail(), 
            passwordEncoder.encode(dto.getPassword())
        );
        
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

    public User getLogged(){
        return ((AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

}
