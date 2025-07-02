package com.cinema.ticket.service.impl;


import com.cinema.ticket.dto.TicketRequest;
import com.cinema.ticket.entity.Film;
import com.cinema.ticket.entity.Ticket;
import com.cinema.ticket.entity.User;
import com.cinema.ticket.exception.ResourceNotFoundException;
import com.cinema.ticket.repository.CategoryRepo;
import com.cinema.ticket.repository.FilmRepo;
import com.cinema.ticket.repository.TicketRepo;
import com.cinema.ticket.repository.UserRepo;
import com.cinema.ticket.service.ITicketService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Data
public class TicketServiceImpl implements ITicketService {
    private final TicketRepo ticketRepo;
    private final CategoryRepo categoryRepo;
    private final FilmRepo filmRepo;
    private final UserRepo userRepo;

    @Override
    public Page<Ticket> getTickets(Pageable pageable) {
        return ticketRepo.findAll(pageable);
    }
    @Override
    public Ticket getTicketById(int id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
    @Override

    public Page<Ticket> getTicketsByUserId(int userId, Pageable pageable) {
        return ticketRepo.findTicketByUserId(userId, pageable);
    }
    @Transactional
    @Override
    public Ticket addTicket(TicketRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Film film = filmRepo.findById(request.getFilmId())
                .orElseThrow(() -> new RuntimeException("Film not found"));

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFilm(film);
        ticket.setSaloonNumber(request.getSaloonNumber());
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setFilmDate(request.getFilmDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime()); // if using Date in DTO; else adjust
        // status defaults to pending

        return ticketRepo.save(ticket);
    }

    @Transactional
    @Override
    public Ticket cancelTicket(int id) {
        Ticket ticket= ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        if ("cancelled".equalsIgnoreCase(ticket.getStatus())) {
            throw new IllegalStateException("Ticket is already cancelled");
        }
        ticket.setStatus("cancelled");
        return ticketRepo.save(ticket);
    }

}
