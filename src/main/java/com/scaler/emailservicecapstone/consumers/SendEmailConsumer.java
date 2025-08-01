package com.scaler.emailservicecapstone.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.scaler.emailservicecapstone.dto.SendEmailDto;
import com.scaler.emailservicecapstone.producers.SendEmailProducer;
import com.scaler.emailservicecapstone.util.EmailUtil;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.scaler.emailservicecapstone.dto.*;

import java.time.LocalDateTime;
import java.util.Properties;

@Component
public class SendEmailConsumer {

    @Autowired
    private SendEmailProducer sendEmailProducer;

    private ObjectMapper mapper;

    public SendEmailConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = "sendEmail", groupId = "email")
    public void sendEmail(String message) {

        SendEmailDto sendEmailDto;
        mapper = new ObjectMapper();

        try {
            sendEmailDto = mapper.readValue(message, SendEmailDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final String fromEmail = sendEmailDto.getFrom(); //requires valid gmail id
        final String password = "wsiyxrbpgpufykde"; // correct password for gmail id
        final String toEmail = sendEmailDto.getTo(); // can be any email id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail, sendEmailDto.getSubject(), sendEmailDto.getBody());

    }

    @KafkaListener(topics = "movie_ticket", groupId = "movie")
    public void sendTicketAsEmail(String message) {

        System.out.println("message from producer: " + message);
        SendTicketEmailDto sendTicketEmailDto;
        mapper = new ObjectMapper();
        mapper.registerModule(
                new JavaTimeModule()
        );

        try {
            sendTicketEmailDto = mapper.readValue(message, SendTicketEmailDto.class);
            System.out.println("TicketEmailDto: " + sendTicketEmailDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final String fromEmail = "sa.abdurrahman2015@gmail.com"; //requires valid gmail id
        final String password = "wsiyxrbpgpufykde"; // correct password for gmail id
        final String toEmail = sendTicketEmailDto.getEmail();
        // can be any email id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail, "BOOKING STATUS", sendTicketEmailDto.toString());

    }


    @KafkaListener(topics = "send-otp", groupId = "otp-sms")
    public void consumeOtpMessages(String message) {

        // Sample message: aadhaar:1234|otp:9876|mobile:+91XXXXXXXXXX|email:abc@mail.com

        String[] parts = message.split("\\|");
        String email = parts[3].split(":")[1];
        String otp = parts[1].split(":")[1];

        System.out.println("SMS OTP: " + otp);
        System.out.println("SMS EMAIL: " + email);

        final String fromEmail = "sa.abdurrahman2015@gmail.com"; //requires valid gmail id
        final String password = "wsiyxrbpgpufykde"; // correct password for gmail id
        final String toEmail = email;
        // can be any email id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        String mailContent = "Your OTP for login is " + otp + ". This otp is valid only for 5 minutes.";

        try {
            EmailUtil.sendEmail(session, toEmail, "OTP STATUS", mailContent);
            handleOtpDelivery(otp, email);
        } catch (Exception e) {
            throw new RuntimeException("OTP could not be sent", e);
        }
    }

    public void handleOtpDelivery(String otp, String email) {
            String message = "";
            OtpAckDto otpAckDto = new OtpAckDto();
            otpAckDto.setOtpValue(otp);
            otpAckDto.setDelivered(true);
            otpAckDto.setDeliveryStatus("SUCCESS");
            otpAckDto.setDeliveryTarget(email);
            otpAckDto.setDeliveryTimestamp(LocalDateTime.now());

            try {
               message  = mapper.writeValueAsString(otpAckDto);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            sendEmailProducer.sendAcknowledgement("otp-ack", message);
    }
}
