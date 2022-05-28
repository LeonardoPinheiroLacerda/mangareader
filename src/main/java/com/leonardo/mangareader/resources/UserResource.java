package com.leonardo.mangareader.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.leonardo.mangareader.dtos.SigninCredentialsDTO;
import com.leonardo.mangareader.dtos.UserDTO;
import com.leonardo.mangareader.services.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        @RequestBody @Valid SigninCredentialsDTO body,
        HttpServletResponse response
    ) throws IOException {

        UserDTO dto = service.create(body);

        if(redirect){
            response.sendRedirect("/login");
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username){
        UserDTO dto = service.findByUsername(username);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable(name = "email") String email){
        UserDTO dto = service.findByEmail(email);
        return ResponseEntity.ok(dto);
    }

}
