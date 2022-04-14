package com.fields.fileds_library.security;

import com.fields.fileds_library.api.LoginApi;
import com.fields.fileds_library.model.JwtRequest;
import com.fields.fileds_library.model.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController implements LoginApi {

    private final LoginService loginService;

    @Override
    public ResponseEntity<JwtResponse> login(JwtRequest jwtRequest) {
        try {
            return ResponseEntity.ok(loginService.getToken(jwtRequest));
        } catch (Exception exc) {
            log.error(exc.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
