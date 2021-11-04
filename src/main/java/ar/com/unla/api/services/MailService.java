package ar.com.unla.api.services;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static Executor executor = Executors.newFixedThreadPool(10);

    @Value("${spring.mail.username}")
    private String FROM;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String body) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage mail = new SimpleMailMessage();

                mail.setFrom(FROM);
                mail.setTo(to);
                mail.setSubject(subject);
                mail.setText(body);

                javaMailSender.send(mail);
            }
        };

        executor.execute(task);
    }
}
