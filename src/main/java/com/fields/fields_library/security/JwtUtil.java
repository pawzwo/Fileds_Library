package com.fields.fields_library.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@PropertySource("classpath:constant.properties")
public class JwtUtil {

    @Value("${secretToken}")
    private String secret;

    @Value("${expiration}")
    private Long expiration;
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public String createToken (UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +expiration))
                .withClaim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        return verifier.verify(token);
    }

    public String parseUserName(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Collection<SimpleGrantedAuthority> parseAuthorities(DecodedJWT decodedJWT) {
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @PostConstruct
    public void populate() {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        verifier = JWT.require(algorithm).build();
    }


}
