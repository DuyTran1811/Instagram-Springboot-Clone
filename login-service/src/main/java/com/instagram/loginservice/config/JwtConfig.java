package com.instagram.loginservice.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class JwtConfig {
    @Value("${configJwt.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${configJwt.jwt.header:Authorization}")
    private String header;

    @Value("${configJwt.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${configJwt.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${configJwt.jwt.secret:JwtSecretKey}")
    private String secret;
}
