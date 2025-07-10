package com.cinema.ticket.service;

import com.cinema.ticket.dto.LoginRequest;
import com.cinema.ticket.dto.LoginResponse;
import com.cinema.ticket.dto.RegisterResponse;
import com.cinema.ticket.dto.UserRegisterRequest;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthService {


    LoginResponse login(LoginRequest loginRequest);

    @Transactional
    RegisterResponse register(UserRegisterRequest request);

}
