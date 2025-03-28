package com.ms.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.model.EmailModel;
import com.ms.email.services.EmailService;






@Component
public class EmailConsumer {

    final EmailService emailService;
    public EmailConsumer(EmailService emailService) {       
        this.emailService = emailService;
    }

    
        
        @RabbitListener(queues = "${broker.queue.email.name}")
        public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
            var emailModel = new EmailModel();
            BeanUtils.copyProperties(emailRecordDto, emailModel);
            emailService.sendEmail(emailModel);
            System.out.println("Email sent successfully!"); 
            
            
            
            
            
            //System.out.println(emailRecordDto.userId());
            //System.out.println(emailRecordDto.emailTo());
            //System.out.println(emailRecordDto.subject());
            //System.out.println(emailRecordDto.text());

                
        
    }
}
