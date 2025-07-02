package com.cinema.ticket.service;

import com.cinema.ticket.dto.LoginRequest;
import com.cinema.ticket.dto.LoginResponse;
import com.cinema.ticket.dto.UserRegisterRequest;
import com.cinema.ticket.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthService {


    LoginResponse login(LoginRequest loginRequest);

    @Transactional
    User register(UserRegisterRequest request);
}
