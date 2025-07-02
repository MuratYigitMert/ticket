package com.cinema.ticket.controller;


import com.cinema.ticket.dto.DtoConverter;
import com.cinema.ticket.dto.TicketRequest;
import com.cinema.ticket.dto.TicketResponse;
import com.cinema.ticket.entity.Ticket;
import com.cinema.ticket.service.ITicketService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {
    private final ITicketService ticketService;
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable int id) {
        Ticket ticket= ticketService.getTicketById(id);
        TicketResponse response = DtoConverter.toDto(ticket);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<TicketResponse>> getTicketsByUserId(@PathVariable int id, Pageable pageable) {
        Page<Ticket> tickets = ticketService.getTicketsByUserId(id, pageable);
        Page<TicketResponse> response = tickets.map(DtoConverter::toDto);
        return ResponseEntity.ok(response);

    }
    @PostMapping
    public ResponseEntity<TicketResponse> addTicket(@RequestBody TicketRequest request) {
        Ticket ticket= ticketService.addTicket(request);
        TicketResponse response = DtoConverter.toDto(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<TicketResponse> cancelTicket(@PathVariable int id) {
        Ticket cancelledTicket = ticketService.cancelTicket(id);
        TicketResponse response = DtoConverter.toDto(cancelledTicket);
        return ResponseEntity.ok(response);
    }

}
