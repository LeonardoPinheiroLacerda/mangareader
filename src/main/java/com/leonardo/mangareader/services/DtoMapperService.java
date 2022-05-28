package com.leonardo.mangareader.services;

import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.models.User;

import org.springframework.stereotype.Service;

@Service
public class DtoMapperService {
    
    public UserDTO UserToUserDTO(User user){
        return new UserDTO(
            user.getId(), 
            user.getUsername(), 
            user.getEmail()
        );
    }

}
