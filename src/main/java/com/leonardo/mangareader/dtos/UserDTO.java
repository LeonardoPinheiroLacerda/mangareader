package com.leonardo.mangareader.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class UserDTO {
    
    private Integer id;
    private String username;
    private String email;

}
