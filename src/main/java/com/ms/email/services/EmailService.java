package com.ms.email.services;

import java.time.LocalDateTime;
import com.ms.email.enums.StatusEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Simple;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.email.model.EmailModel;
import com.ms.email.repositories.EmailRepository;



@Service
public class EmailService {

    final EmailRepository emailRepository;
    final JavaMailSender eMailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender eMailSender) {
        this.emailRepository = emailRepository;
        this.eMailSender = eMailSender;
    }

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            eMailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);

            
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }

}
