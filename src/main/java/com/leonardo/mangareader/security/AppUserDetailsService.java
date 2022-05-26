package com.leonardo.mangareader.security;

import com.leonardo.mangareader.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service
public class AppUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AppUserDetails(
            userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Não foi possível localizar um usuário com esse username."))
        );
    }
  
}
