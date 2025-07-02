package com.cinema.ticket.repository;

import com.cinema.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepo extends JpaRepository<Ticket,Integer> {
    Page<Ticket> findTicketByUserId(Integer userId, Pageable pageable);
}
