package com.pejjok.blog.controlers;

import com.pejjok.blog.domain.dtos.AuthResponse;
import com.pejjok.blog.domain.dtos.LoginRequest;
import com.pejjok.blog.domain.dtos.RegisterRequest;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.mappers.UserMapper;
import com.pejjok.blog.services.AuthenticationService;
import com.pejjok.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationService authenticationService, UserService userService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authentication(@RequestBody LoginRequest loginRequest){
        UserDetails userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        AuthResponse authResponse = AuthResponse.builder()
                .token(authenticationService.generateToken(userDetails))
                .expiredIn(86400)
                .build();
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
        UserEntity user = userMapper.toEntity(registerRequest);
        userService.createUser(user);
        UserDetails userDetails = authenticationService.authenticate(registerRequest.getEmail(), registerRequest.getPassword());
        AuthResponse authResponse = AuthResponse.builder()
                .token(authenticationService.generateToken(userDetails))
                .expiredIn(86400)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }
}
