package com.company.library.service;

import com.company.library.DTO.Registration;
import com.company.library.model.User;
import com.company.library.model.UserBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

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

    public void sendWelcomeEmail(User user) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Welcome to library!");
            Context context = new Context();
            context.setVariable("firstName", user.getFirstName());
            context.setVariable("logo", "logo");
            context.setVariable("pen", "pen");
            String content = templateEngine.process("welcomeEmailTemplate", context);
            messageHelper.setText(content, true);
            FileSystemResource pen = new FileSystemResource(new File("src/main/resources/logo-black-pen.png"));
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/logo-black.png"));
            messageHelper.addInline("logo", logo, "image/png");
            messageHelper.addInline("pen", pen, "image/png");
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
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(userBook.getUser().getEmail());
            messageHelper.setSubject("You just borrowed a book");
            Context context = new Context();
            context.setVariable("firstName", userBook.getUser().getFirstName());
            context.setVariable("bookTitle", userBook.getBook().getTitle());
            context.setVariable("returnDate", userBook.getReturn_date());
            context.setVariable("logo", "logo");
            context.setVariable("pen", "pen");
            String content = templateEngine.process("borrowedBookTemplate", context);
            messageHelper.setText(content, true);
            FileSystemResource pen = new FileSystemResource(new File("src/main/resources/logo-black-pen.png"));
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/logo-black.png"));
            messageHelper.addInline("logo", logo, "image/png");
            messageHelper.addInline("pen", pen, "image/png");
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }

    public void sendActivateAccountEmail(User user, String code) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Activate account");
            Context context = new Context();
            context.setVariable("firstName", user.getFirstName());
            context.setVariable("code", code);
            context.setVariable("logo", "logo");
            context.setVariable("pen", "pen");
            String content = templateEngine.process("activateAccountTemplate", context);
            messageHelper.setText(content, true);
            FileSystemResource pen = new FileSystemResource(new File("src/main/resources/logo-black-pen.png"));
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/logo-black.png"));
            messageHelper.addInline("logo", logo, "image/png");
            messageHelper.addInline("pen", pen, "image/png");
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendAlmostReturnDateEmail(UserBook userBook) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(userBook.getUser().getEmail());
            messageHelper.setSubject("You must return your book!");
            Context context = new Context();
            context.setVariable("bookTitle", userBook.getBook().getTitle());
            context.setVariable("firstName", userBook.getUser().getFirstName());
            context.setVariable("logo", "logo");
            context.setVariable("pen", "pen");
            String content = templateEngine.process("almostReturnDateTemplate", context);
            messageHelper.setText(content, true);
            FileSystemResource pen = new FileSystemResource(new File("src/main/resources/logo-black-pen.png"));
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/logo-black.png"));
            messageHelper.addInline("logo", logo, "image/png");
            messageHelper.addInline("pen", pen, "image/png");
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }

    public void sendResetPasswordEmail(User user, String key) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Reset password");
            Context context = new Context();
            context.setVariable("firstName", user.getFirstName());
            context.setVariable("key", key);
            context.setVariable("logo", "logo");
            context.setVariable("pen", "pen");
            String content = templateEngine.process("resetPasswordTemplate", context);
            messageHelper.setText(content, true);
            FileSystemResource pen = new FileSystemResource(new File("src/main/resources/logo-black-pen.png"));
            FileSystemResource logo = new FileSystemResource(new File("src/main/resources/logo-black.png"));
            messageHelper.addInline("logo", logo, "image/png");
            messageHelper.addInline("pen", pen, "image/png");
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            System.out.println(e.getMessage());
        }
    }
}

