package com.cinema.ticket.controller;

import com.cinema.ticket.dto.DtoConverter;
import com.cinema.ticket.dto.FilmResponse;
import com.cinema.ticket.entity.Film;
import com.cinema.ticket.service.IFilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {
    private final IFilmService filmService;

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> findById(@PathVariable int id) {
        Film film = filmService.findById(id);
        return ResponseEntity.ok(DtoConverter.toDto(film));
    }
    @GetMapping
    public ResponseEntity<Page<FilmResponse>> findAllFilms(Pageable pageable) {
        Page<Film> films = filmService.findAllFilms(pageable);
        Page<FilmResponse> response = films.map(DtoConverter::toDto);
        return ResponseEntity.ok(response);
    }
//    @PostMapping
//    public ResponseEntity<FilmResponse> addFilm(@RequestBody FilmRequest request) {
//        Film film= new Film();
//        film.setName(request.getName());
//        film.setCategory(categoryService.findbyId(request.getCategoryId()));
//        return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.toDto(filmService.addFilm(film)));
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<FilmResponse> updateFilm(@PathVariable int id,@RequestBody FilmRequest request) {
//        Film film= new Film();
//        film.setName(request.getName());
//        film.setCategory(categoryService.findbyId(request.getCategoryId()));
//        return ResponseEntity.ok(DtoConverter.toDto(filmService.updateFilm(id, film)));
//
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteFilm(@PathVariable int id) {
//        filmService.deleteFilm(id);
//        return ResponseEntity.ok("Film with id " + id + " deleted successfully.");
//    }
    @GetMapping("/search")
    public ResponseEntity<Page<FilmResponse>> findAllFilmsByCategoryAndText(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) List<String> categoryNames,
            Pageable pageable) {
        Page<FilmResponse> result = filmService.findAllFilmsByCategoryAndText(searchText,categoryNames,pageable).map(DtoConverter::toDto);
        return ResponseEntity.ok(result);
    }
}
