package com.company.library.service;

import com.company.library.model.User;
import com.company.library.model.UserBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(SimpleMailMessage msg) {
        javaMailSender.send(msg);
    }

//    public String build(User user) {
//        Context context = new Context();
//        context.setVariable("firstName", user.getFirstName());
//        return templateEngine.process("mailTemplate", context);
//    }

    public void sendWelcomeEmail(User user) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Sample mail subject");
            Context context = new Context();
            context.setVariable("firstName",user.getFirstName());
            String content = templateEngine.process("welcomeEmailTemplate",context);
            messageHelper.setText(content, true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }

    public void sendBorrowEmail(UserBook userBook) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(userBook.getUser().getEmail());
            messageHelper.setSubject("Sample mail subject");
            Context context = new Context();
            context.setVariable("firstName",userBook.getUser().getFirstName());
            context.setVariable("bookTitle", userBook.getBook().getTitle());
            context.setVariable("returnDate", userBook.getReturn_date());
            String content = templateEngine.process("borrowedBookTemplate",context);
            messageHelper.setText(content, true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }

    public void sendActivateAccountEmail(User user, String link){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Sample mail subject");
            Context context = new Context();
            context.setVariable("firstName", user.getFirstName());
            context.setVariable("link", link);
            String content = templateEngine.process("activateAccountTemplate",context);
            messageHelper.setText(content, true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }

    }

    public void sendAlmostReturnDateEmail(UserBook userBook){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(userBook.getUser().getEmail());
            messageHelper.setSubject("Sample mail subject");
            Context context = new Context();
            context.setVariable("bookTitle", userBook.getBook().getTitle());
            context.setVariable("firstName", userBook.getUser().getFirstName());
            String content = templateEngine.process("almostReturnDateTemplate",context);
            messageHelper.setText(content, true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }

    public void sendResetPasswordEmail(User user, String key){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Sample mail subject");
            Context context = new Context();
            context.setVariable("firstName",user.getFirstName());
            context.setVariable("key", key);
            String content = templateEngine.process("resetPasswordTemplate", context);
            messageHelper.setText(content, true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }
}

