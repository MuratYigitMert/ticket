package com.cinema.ticket.dto;

import com.cinema.ticket.entity.Film;
import com.cinema.ticket.entity.Role;
import com.cinema.ticket.entity.Ticket;
import com.cinema.ticket.entity.User;

public class DtoConverter {
    private DtoConverter() {
    }


    public static UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setRoleId(user.getRole().getId());
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
    public static TicketResponse toDto(Ticket ticket) {
        TicketResponse dto = new TicketResponse();
        dto.setId(ticket.getId());
        dto.setUser(toDto(ticket.getUser()));
        dto.setFilm(toDto(ticket.getFilm()));
        dto.setSaloonNumber(ticket.getSaloonNumber());
        dto.setFilmDate(ticket.getFilmDate());
        dto.setSeatNumber(ticket.getSeatNumber());
        return dto;
    }

    public static RoleResponse toDto(Role role) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        return roleResponse;
    }
    public static FilmResponse toDto(Film film) {
        FilmResponse filmResponse = new FilmResponse();
        filmResponse.setId(film.getId());
        filmResponse.setName(film.getName());
        filmResponse.setCategoryId(film.getCategory().getId());
        return filmResponse;

    }
    public static User toEntity(UserRequest request, Role role) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole(role);
        return user;
    }

}

