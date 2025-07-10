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
import com.cinema.ticket.service.IMailService;
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
    private final IMailService mailService;
    @Override
    public Page<Ticket> getTickets(Pageable pageable) {
        return ticketRepo.findAll(pageable);
    }
    @Override
    public Ticket getTicketById(int id) {
        return ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }
    @Override

    public Page<Ticket> getTicketsByUserId(int userId, Pageable pageable) {
        return ticketRepo.findTicketByUserId(userId, pageable);
    }
    @Transactional
    @Override
    public Ticket addTicket(TicketRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Film film = filmRepo.findById(request.getFilmId())
                .orElseThrow(() -> new ResourceNotFoundException("Film not found"));

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFilm(film);
        ticket.setSaloonNumber(request.getSaloonNumber());
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setFilmDate(request.getFilmDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime()); // if using Date in DTO; else adjust
        ticket.setPersonCount(request.getPersonCount());
        ticket.setPaymentStatus("COMPLETED");
        ticket.setStatus("ACTIVE");
        Ticket savedTicket = ticketRepo.save(ticket);
        try {
            mailService.sendTicketConfirmation(
                    user.getEmail(),
                    film.getName(),
                    request.getSeatNumber(),
                    ticket.getFilmDate().toString()
            );
        }  catch (Exception e) {
            // Log warning but don't block ticket creation
            System.out.println("Failed to send email:"+ e.getMessage());
        }
        return savedTicket;
    }

    @Transactional
    @Override
    public Ticket cancelTicket(int id) {
        Ticket ticket= ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
        if ("cancelled".equalsIgnoreCase(ticket.getStatus())) {
            throw new IllegalStateException("Ticket is already cancelled");
        }
        ticket.setStatus("cancel_requested");
        mailService.sendCancellationRequestToAdmin(
                ticket.getUser().getEmail(),
                ticket.getFilm().getName(),
                ticket.getSeatNumber(),
                ticket.getFilmDate().toString()
        );
        return ticketRepo.save(ticket);
    }
    @Override
    public Ticket approveCancellation(int id) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (!"cancellation_requested".equalsIgnoreCase(ticket.getStatus())) {
            throw new IllegalStateException("No cancellation was requested");
        }

        ticket.setStatus("cancelled");
        Ticket saved = ticketRepo.save(ticket);

        mailService.sendCancellationApprovedToUser(
                ticket.getUser().getEmail(),
                ticket.getFilm().getName()
        );

        return saved;
    }
    @Override
    public Ticket rejectCancellation(int id) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (!"cancel_requested".equalsIgnoreCase(ticket.getStatus())) {
            throw new IllegalStateException("No cancellation was requested");
        }

        // Set status back to "active" or whatever is appropriate
        ticket.setStatus("active");
        Ticket saved = ticketRepo.save(ticket);

        // Notify user that cancellation was rejected
        mailService.sendCancellationRejectedToUser(
                ticket.getUser().getEmail(),
                ticket.getFilm().getName()
        );

        return saved;
    }



}
