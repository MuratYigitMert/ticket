package com.cinema.ticket.controller;

import com.cinema.ticket.dto.*;
import com.cinema.ticket.entity.Film;
import com.cinema.ticket.entity.Role;
import com.cinema.ticket.entity.Ticket;
import com.cinema.ticket.entity.User;
import com.cinema.ticket.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('admin')")
public class AdminController {

    private final IFilmService filmService;
    private final ICategoryService categoryService;
    private final IUserService userService;
    private final IRoleService roleService;
    private final ITicketService ticketService;

    // FILM OPERATIONS
    @PostMapping("/films")
    public ResponseEntity<FilmResponse> addFilm(@RequestBody FilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setCategory(categoryService.findbyId(request.getCategoryId()));
        film.setPosterUrl(request.getPosterUrl());
        film.setTrailerUrl(request.getTrailerUrl());
        film.setDescription(request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoConverter.toDto(filmService.addFilm(film)));
    }

    @PutMapping("/films/{id}")
    public ResponseEntity<FilmResponse> updateFilm(@PathVariable int id, @RequestBody FilmRequest request) {
        Film film = new Film();
        film.setName(request.getName());
        film.setCategory(categoryService.findbyId(request.getCategoryId()));
        film.setPosterUrl(request.getPosterUrl());
        film.setTrailerUrl(request.getTrailerUrl());
        film.setDescription(request.getDescription());
        return ResponseEntity.ok(DtoConverter.toDto(filmService.updateFilm(id, film)));
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable int id) {
        filmService.deleteFilm(id);
        return ResponseEntity.ok("Film with id " + id + " deleted successfully.");
    }

    // USER OPERATIONS
    @PostMapping("/users")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request) {
        Role role = roleService.findRoleById(request.getRoleId());
        User user = DtoConverter.toEntity(request, role);
        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(DtoConverter.toDto(savedUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> modifyUser(@PathVariable int id, @RequestBody UserRequest request) {
        Role role = roleService.findRoleById(request.getRoleId());
        User user = DtoConverter.toEntity(request, role);
        return ResponseEntity.ok(DtoConverter.toDto(userService.modifyUser(id, user)));
    }

    @PutMapping("/users/change-role")
    public ResponseEntity<UserResponse> changeUserRole(@RequestBody RoleChangeRequest request) {
        User updatedUser = userService.changeUserRole(request.getId(), request.getRole());
        return ResponseEntity.ok(DtoConverter.toDto(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable int id) {
        userService.deleteUserbyId(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        Page<UserResponse> response = users.map(DtoConverter::toDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(DtoConverter.toDto(userService.getUserbyId(id)));
    }
    @GetMapping
    public ResponseEntity<Page<TicketResponse>> getTickets(Pageable pageable) {
        Page<Ticket> tickets = ticketService.getTickets(pageable);
        Page<TicketResponse> response = tickets.map(DtoConverter::toDto);
        return ResponseEntity.ok(response);
    }
}
