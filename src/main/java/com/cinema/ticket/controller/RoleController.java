package com.cinema.ticket.controller;

import com.cinema.ticket.dto.DtoConverter;
import com.cinema.ticket.dto.RoleResponse;
import com.cinema.ticket.entity.Role;
import com.cinema.ticket.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    @GetMapping
     ResponseEntity<Page<RoleResponse>> getAllRoles(Pageable pageable){

        Page<Role> roles = roleService.findAllRoles(pageable);
        Page<RoleResponse> response = roles.map(DtoConverter::toDto);
        return ResponseEntity.ok(response);

    }
}
