package com.cinema.ticket.controller;

import com.cinema.ticket.dto.*;
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
    private final IUserService userService;
    private final ITicketService ticketService;
    private final IMailService mailService;
    // FILM OPERATIONS
    @PostMapping("/films")
    public ResponseEntity<FilmResponse> addFilm(@RequestBody FilmRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DtoConverter.toDto(filmService.addFilm(request)));
    }


    @PutMapping("/films/{id}")
    public ResponseEntity<FilmResponse> updateFilm(@PathVariable int id, @RequestBody FilmRequest request) {
        return ResponseEntity.ok(DtoConverter.toDto(filmService.updateFilm(id, request)));
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable int id) {
        filmService.deleteFilm(id);
        return ResponseEntity.ok("Film with id " + id + " deleted successfully.");
    }

    // USER OPERATIONS
    @PostMapping("/users")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request) {
        User savedUser = userService.addUser(request);
        return ResponseEntity.ok(DtoConverter.toDto(savedUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> modifyUser(@PathVariable int id, @RequestBody UserRequest request) {
        User updatedUser = userService.modifyUser(id, request); // just pass the DTO
        return ResponseEntity.ok(DtoConverter.toDto(updatedUser));
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
    }@GetMapping("/test-mail")
    public ResponseEntity<String> testMail() {
        mailService.sendTicketConfirmation("yigit.mert@ozu.edu.tr", "Inception", 2, "2025-07-20 19:00");
        return ResponseEntity.ok("Email sent!");
    }
    @PutMapping("/tickets/{id}/approve-cancellation")
    public ResponseEntity<TicketResponse> approveCancellation(@PathVariable int id) {
        Ticket ticket = ticketService.approveCancellation(id);
        return ResponseEntity.ok(DtoConverter.toDto(ticket));
    }
    @PutMapping("/tickets/{id}/reject-cancellation")
    public ResponseEntity<TicketResponse> rejectCancellation(@PathVariable int id) {
        Ticket ticket = ticketService.rejectCancellation(id);
        return ResponseEntity.ok(DtoConverter.toDto(ticket));
    }



}
