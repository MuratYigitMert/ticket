package com.cinema.ticket.service.impl;


import com.cinema.ticket.entity.Film;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.repository.FilmRepo;
import com.cinema.ticket.service.IFilmService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements IFilmService {
    private final FilmRepo filmRepo;
    @Override
    public Film findById(int id) {
        return filmRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + id));
    }
    @Override
    public Page<Film> findAllFilms(Pageable pageable){
        return filmRepo.findAll(pageable);
    }
    @Transactional
    @Override
    public Film addFilm(Film film){
        return filmRepo.save(film);
    }
    @Transactional
    @Override
    public Film updateFilm(int id, Film film){
        Film film1 = filmRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + id));
        film1.setName(film.getName());
        film1.setCategory(film.getCategory());
        return filmRepo.save(film1);

    }
    @Transactional
    @Override
    public void deleteFilm(int id){
        filmRepo.deleteById(id);
    }
    @Override
    public Page<Film> findAllFilmsByCategoryAndText(String searchText, List<String> categoryNames, Pageable pageable){
        if ((searchText == null || searchText.isEmpty()) && (categoryNames == null || categoryNames.isEmpty())) {
            return filmRepo.findAll(pageable);
        }
        if (categoryNames != null && categoryNames.isEmpty()) {
            categoryNames = null;
        }

        return filmRepo.findByCategoryOrFilmNames(
                searchText != null ? searchText : "",
                categoryNames != null ? categoryNames.stream().map(String::toLowerCase).toList() : null,
                pageable);
    }

}
