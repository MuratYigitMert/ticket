package com.cinema.ticket.service.impl;

import com.cinema.ticket.entity.Category;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.repository.CategoryRepo;
import com.cinema.ticket.service.ICategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public Category findbyId(int id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
