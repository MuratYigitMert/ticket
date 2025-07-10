package com.cinema.ticket.controller;

import com.cinema.ticket.dto.LoginRequest;
import com.cinema.ticket.dto.LoginResponse;
import com.cinema.ticket.dto.RegisterResponse;
import com.cinema.ticket.dto.UserRegisterRequest;
import com.cinema.ticket.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserRegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
