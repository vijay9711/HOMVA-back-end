package com.example.realEstate.service;

import com.example.realEstate.entity.User;
import com.example.realEstate.entity.dto.request.LoginRequest;
import com.example.realEstate.entity.dto.request.RefreshTokenRequest;
import com.example.realEstate.entity.dto.request.SignupRequest;
import com.example.realEstate.entity.dto.response.LoginResponse;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest) throws ChangeSetPersister.NotFoundException;
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
   User signup(SignupRequest signupRequest) throws IOException;
}
