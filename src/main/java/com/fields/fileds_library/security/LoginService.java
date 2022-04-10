package com.fields.fileds_library.security;

import com.fields.fileds_library.model.JwtRequest;
import com.fields.fileds_library.model.JwtResponse;

public interface LoginService {

    JwtResponse getToken(JwtRequest jwtRequest) throws Exception;

    void authenticate(String userName, String password) throws Exception;

}
