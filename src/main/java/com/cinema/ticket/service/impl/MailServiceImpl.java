package com.cinema.ticket.service.impl;

import com.cinema.ticket.service.IMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements IMailService {

    private final JavaMailSender mailSender;
    @Override
    public void sendTicketConfirmation(String toEmail, String filmName, int seatNumber, String dateTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Bilet Rezervasyon Onayı");
        message.setText("Sayın müşterimiz,\n\n" +
                "Film: " + filmName + "\n" +
                "Koltuk: " + seatNumber + "\n" +
                "Tarih: " + dateTime + "\n\n" +
                "Rezervasyonunuz başarıyla alınmıştır.");

        mailSender.send(message);
    }
    @Override
    public void sendCancellationRequestToAdmin(String userEmail, String filmName, int seatNumber, String filmDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com");
        message.setSubject("Bilet İptal Talebi");
        message.setText("""
            Bir kullanıcı biletini iptal etmek istiyor.

            Kullanıcı: %s
            Film: %s
            Koltuk No: %d
            Tarih: %s

            Lütfen sistemden bu talebi inceleyip onaylayın veya reddedin.
            """.formatted(userEmail, filmName, seatNumber, filmDate));

        mailSender.send(message);
    }
    @Override
    public void sendCancellationRejectedToUser(String userEmail, String filmName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Bilet İptal Talebiniz Reddedildi");
        message.setText(String.format(
                "İptal etmek istediğiniz %s filminize ait bilet iptal talebiniz reddedilmiştir. " +
                        "Biletiniz aktif durumda kalmaya devam edecektir.", filmName));
        mailSender.send(message);
    }
    @Override
    public void sendCancellationApprovedToUser(String userEmail, String filmName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Bilet İptal Talebiniz Onaylandı");
        message.setText(String.format(
                "İptal etmek istediğiniz %s filminize ait bilet iptal talebiniz onaylanmıştır. " +
                        "İptal işlemi gerçekleştirilmiştir. Teşekkür ederiz.",
                filmName));
        mailSender.send(message);
    }


}
