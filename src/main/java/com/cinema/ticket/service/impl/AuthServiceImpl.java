package com.cinema.ticket.service.impl;

import com.cinema.ticket.auth.JwtUtil;
import com.cinema.ticket.dto.LoginRequest;
import com.cinema.ticket.dto.LoginResponse;
import com.cinema.ticket.dto.RegisterResponse;
import com.cinema.ticket.dto.UserRegisterRequest;
import com.cinema.ticket.entity.Role;
import com.cinema.ticket.entity.User;
import com.cinema.ticket.exception.AuthenticationException;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.repository.RoleRepo;
import com.cinema.ticket.repository.UserRepo;
import com.cinema.ticket.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            User user = userRepo.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }

            String token = jwtUtil.createToken(user);
            return new LoginResponse(token, user.getUsername(), user.getEmail());

        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid username/password");
        }
    }

    @Transactional
    @Override
    public RegisterResponse  register(UserRegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        Role role = roleRepo.findByName("user")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));
        user.setRole(role);

        User savedUser = userRepo.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRoleId(savedUser.getRole().getId());

        return response;
    }
}
