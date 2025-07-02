package com.cinema.ticket.service.impl;


import com.cinema.ticket.entity.Role;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.repository.RoleRepo;
import com.cinema.ticket.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final RoleRepo roleRepo;
    @Override
    public Role findRoleById(int id) {
        System.out.println("Looking for role with id: " + id);
        return roleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
    @Override
    public Page<Role> findAllRoles(Pageable pageable){
        return roleRepo.findAll(pageable);
    }
}
