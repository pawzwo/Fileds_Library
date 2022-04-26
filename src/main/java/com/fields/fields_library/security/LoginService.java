package com.fields.fields_library.security;

import com.fields.fields_library.model.JwtRequest;
import com.fields.fields_library.model.JwtResponse;

public interface LoginService {

    JwtResponse getToken(JwtRequest jwtRequest) throws Exception;

    void authenticate(String userName, String password) throws Exception;

}
