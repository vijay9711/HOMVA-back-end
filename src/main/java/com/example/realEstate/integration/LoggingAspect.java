package com.example.realEstate.integration;

import com.example.realEstate.entity.Log;
import com.example.realEstate.repository.LogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private final LogRepository logRepository;

    public LoggingAspect(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Before("execution(* com.example.realEstate.controller.AuthController.login(..)) && args(loginRequest)")
    public void logLogin(JoinPoint joinPoint, com.example.realEstate.entity.dto.request.LoginRequest loginRequest) {
        // Get the user's email from the login request
        String userEmail = loginRequest.getEmail();

        // Create a LogEntity object with user email, method name, and current date/time
        Log logEntity = new Log();
        logEntity.setUserEmail(userEmail);
        logEntity.setMethodName(joinPoint.getSignature().getName());
        logEntity.setDateTime(LocalDateTime.now());

        // Save the logEntity object to the database
        logRepository.save(logEntity);
    }
}
