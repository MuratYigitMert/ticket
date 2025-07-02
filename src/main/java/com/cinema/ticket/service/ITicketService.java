package com.cinema.ticket.service;

import com.cinema.ticket.dto.TicketRequest;
import com.cinema.ticket.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITicketService {


    Page<Ticket> getTickets(Pageable pageable);

    Ticket getTicketById(int id);

    Page<Ticket> getTicketsByUserId(int userId, Pageable pageable);

    @Transactional
    Ticket addTicket(TicketRequest request);

    @Transactional
    Ticket cancelTicket(int id);
}
