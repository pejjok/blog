package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.AuthResponse;
import com.pejjok.blog.domain.dtos.LoginRequest;
import com.pejjok.blog.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> authentication(@RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        AuthResponse authResponse = AuthResponse.builder()
                .token(authenticationService.generateToken(userDetails))
                .expiredIn(86400)
                .build();
        return ResponseEntity.ok(authResponse);
    }
}
