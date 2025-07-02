package com.cinema.ticket.service;

import com.cinema.ticket.entity.Film;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFilmService {
    Film findById(int id);

    Page<Film> findAllFilms(Pageable pageable);

    @Transactional
    Film addFilm(Film film);

    @Transactional
    Film updateFilm(int id, Film film);

    @Transactional
    void deleteFilm(int id);

    Page<Film> findAllFilmsByCategoryAndText(String searchText, List<String> categoryNames, Pageable pageable);
}
