package com.cinema.ticket.service.impl;


import com.cinema.ticket.dto.FilmRequest;
import com.cinema.ticket.entity.Category;
import com.cinema.ticket.entity.Film;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.repository.FilmRepo;
import com.cinema.ticket.service.ICategoryService;
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
    private final ICategoryService categoryService;
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
    public Film addFilm(FilmRequest request) {
        Category category = categoryService.findbyId(request.getCategoryId());

        Film film = new Film();
        film.setName(request.getName());
        film.setCategory(category);
        film.setPosterUrl(request.getPosterUrl());
        film.setTrailerUrl(request.getTrailerUrl());
        film.setDescription(request.getDescription());

        return filmRepo.save(film);
    }
    @Transactional
    @Override
    public Film updateFilm(int id, FilmRequest request) {
        Film existing = filmRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film not found"));

        existing.setName(request.getName());
        existing.setCategory(categoryService.findbyId(request.getCategoryId()));
        existing.setPosterUrl(request.getPosterUrl());
        existing.setTrailerUrl(request.getTrailerUrl());
        existing.setDescription(request.getDescription());

        return filmRepo.save(existing);
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
