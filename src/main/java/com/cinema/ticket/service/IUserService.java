package com.cinema.ticket.service;

import com.cinema.ticket.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IUserService {
    Page<User> getAllUsers(Pageable pageable);

    User getUserbyId(int id);
    User changeUserRole(int id, String role);

    void deleteUserbyId(int id);

    User modifyUser(int id, User user);

    User addUser(User user);
}
