package com.example.realEstate.controller;

import com.example.realEstate.entity.User;
import com.example.realEstate.entity.dto.request.LoginRequest;
import com.example.realEstate.entity.dto.request.RefreshTokenRequest;
import com.example.realEstate.entity.dto.request.SignupRequest;
import com.example.realEstate.entity.dto.response.LoginResponse;
import com.example.realEstate.service.AuthService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws ChangeSetPersister.NotFoundException {
        var loginResponse = authService.login(loginRequest);
        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        try {
            User user = authService.signup(signupRequest);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IOException e) {

            return new ResponseEntity<>("duplicateID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/refresh-token")
    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

}