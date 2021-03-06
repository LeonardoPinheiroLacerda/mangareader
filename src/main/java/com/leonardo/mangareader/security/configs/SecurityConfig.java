package com.leonardo.mangareader.security.configs;


import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final static String[] GET_WHITELIST = new String[]
    {
        "/login",
        "/signin",
        "/assets/*",
        "/js/**",
        "/css/**",
        "/favicon.ico",
        "/api/users/username/*",
        "/api/users/email/*",
    };

    private final static String[] POST_WHITELIST = new String[]{
        "/api/users"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RemembermeProperties rememberMeProperties) throws Exception{

        http.csrf().disable()

        .authorizeHttpRequests()
        .antMatchers(HttpMethod.GET, GET_WHITELIST).permitAll()
        .antMatchers(HttpMethod.POST, POST_WHITELIST).permitAll()
            .anyRequest().authenticated()

        .and()
        .formLogin()
 
        .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")

        .and()
        .rememberMe()
            .rememberMeParameter("rememberme")
            .rememberMeCookieName(rememberMeProperties.getCookieName())
            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(rememberMeProperties.getValidityDays()))
            .key(rememberMeProperties.getKey())

        .and()
        .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.POST.name()))
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID", rememberMeProperties.getCookieName())
            .logoutSuccessUrl("/login");

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(5);
    }    

}
