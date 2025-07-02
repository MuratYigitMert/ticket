package com.cinema.ticket.dto;

import com.cinema.ticket.entity.Film;
import com.cinema.ticket.entity.User;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TicketResponse {
    private int id;
    private UserResponse user;
    private FilmResponse film;
    private int saloonNumber;
    private LocalDateTime filmDate;
    private int seatNumber;
}
