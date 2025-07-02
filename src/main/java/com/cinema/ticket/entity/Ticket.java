package com.cinema.ticket.entity;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor



@Table(name = "tickets")

public class Ticket {
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum TicketStatus {
        pending,
        active,
        cancelled,
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int saloonNumber;
    @Column(nullable = false)
    private LocalDateTime filmDate;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ticket_status")
    private TicketStatus status= TicketStatus.pending;
    @Column(nullable = false)
    private int seatNumber;
    @ManyToOne
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "film_Id", nullable = false)
    private Film film;
}
