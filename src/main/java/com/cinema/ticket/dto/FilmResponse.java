package com.cinema.ticket.dto;

import lombok.Data;

@Data
public class FilmResponse {
    private int id;
    private String name;
    private int categoryId;
    private String posterUrl;
    private String trailerUrl;
    private String description;
}
