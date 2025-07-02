package com.cinema.ticket.service;

import com.cinema.ticket.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    Role findRoleById(int id);

    Page<Role> findAllRoles(Pageable pageable);
}
