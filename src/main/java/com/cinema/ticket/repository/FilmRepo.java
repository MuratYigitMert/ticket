package com.cinema.ticket.repository;

import com.cinema.ticket.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepo extends JpaRepository<Film,Integer> {
    @Query("SELECT f FROM Film f WHERE " +
            "(:searchText IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :searchText, '%'))) " +
            "AND (:categoryNames IS NULL OR LOWER(f.category.name) IN :categoryNames)")
    Page<Film> findByCategoryOrFilmNames(
            @Param("searchText") String searchText,
            @Param("categoryNames") List<String> categoryNames,
            Pageable pageable);
}
