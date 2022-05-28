package com.leonardo.mangareader.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.leonardo.mangareader.dtos.SigninCredentialsDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> create(
        @RequestParam(required = false, defaultValue = "false") Boolean redirect, 
        @RequestBody SigninCredentialsDTO body,
        HttpServletResponse response
    ) throws IOException {

        System.out.println(body);

        UserDTO dto = service.create(body);



        if(redirect){
            response.sendRedirect("/login");
        }
        return ResponseEntity.ok(dto);
    }

}
