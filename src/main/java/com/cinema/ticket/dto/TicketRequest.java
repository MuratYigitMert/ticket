package com.cinema.ticket.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TicketRequest {
    private int userId;
    private int filmId;
    private int saloonNumber;
    private Date filmDate;
    private int seatNumber;
    private int personCount;
    private String paymentStatus;
}
