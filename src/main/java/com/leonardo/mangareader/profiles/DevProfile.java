package com.leonardo.mangareader.profiles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Configuration
@Profile("dev")
public class DevProfile implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.findByUsername("admin").isEmpty()){
            User user = new User(
                null, 
                "admin", 
                "admin@admins.com", 
                passwordEncoder.encode("admin")
            );

            userRepository.save(user);
        }
  
    }
    
}
