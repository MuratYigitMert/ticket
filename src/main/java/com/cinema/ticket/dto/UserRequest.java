package com.cinema.ticket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private int roleId;

}
