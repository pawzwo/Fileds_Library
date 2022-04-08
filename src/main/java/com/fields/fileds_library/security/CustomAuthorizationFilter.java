package com.fields.fileds_library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthorizationFilter {

    private static final String PREFIX = "Bearer";
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

}
