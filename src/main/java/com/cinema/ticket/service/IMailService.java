package com.cinema.ticket.service;

public interface IMailService  {
    void sendTicketConfirmation(String toEmail, String filmName, int seatNumber, String dateTime);

    void sendCancellationRequestToAdmin(String userEmail, String filmName, int seatNumber, String filmDate);

    void sendCancellationRejectedToUser(String userEmail, String filmName);

    void sendCancellationApprovedToUser(String userEmail, String filmName);
}
