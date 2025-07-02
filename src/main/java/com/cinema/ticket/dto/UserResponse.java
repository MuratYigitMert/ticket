package com.cinema.ticket.dto;


import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String username;
    private String email;
    private int roleId;
}
