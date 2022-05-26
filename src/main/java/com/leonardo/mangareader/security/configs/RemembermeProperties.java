package com.leonardo.mangareader.security.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "application.security.rememberme")
public class RemembermeProperties {
    
    private String key;
    private String validityDays;
    private String cookieName;

    public Long getValidityDays(){
        return Long.valueOf(this.validityDays);
    }

}
