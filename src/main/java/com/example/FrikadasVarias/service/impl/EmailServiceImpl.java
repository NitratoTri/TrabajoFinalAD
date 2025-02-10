package com.example.FrikadasVarias.service.impl;

import com.example.FrikadasVarias.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    private String emisor = "pablodaniel.s374378@cesurformacion.com";

    @Override
    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        String toAddress = destinatario;
        String fromAddress = emisor;
        String senderName = "Pablo Daniel";
        String subject = asunto;
        String content = mensaje;
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        emailSender .send(message);
    }
}