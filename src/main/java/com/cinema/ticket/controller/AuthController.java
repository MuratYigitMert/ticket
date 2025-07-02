package com.cinema.ticket.controller;

import com.cinema.ticket.dto.LoginRequest;
import com.cinema.ticket.dto.LoginResponse;
import com.cinema.ticket.dto.RegisterResponse;
import com.cinema.ticket.dto.UserRegisterRequest;
import com.cinema.ticket.entity.User;
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
        User user = authService.register(request);
        RegisterResponse response = new RegisterResponse();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRoleId(user.getRole().getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
