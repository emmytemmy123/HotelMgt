package fcmb.com.good.utills;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class EmailUtils {

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text, List<String> list){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("emmadexboy4real@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(list!=null && list.size() > 0)
        message.setCc(getCcArray(list));

        emailSender.send(message);
    }

    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for(int i =0; i < ccList.size(); i++ ){
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void forgotMail(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("emmadexboy4real@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p> <b>Your Login Details for Hotel Management System</b><br> <b>Email: </b> "+to+" <br>" +
                " <b>Password: </b> "+password+" <br> <a href=\"http://localhost:4200/\"> Click here to Login </a> </p>";
        message.setContent(htmlMsg, "text/html");
        emailSender.send(message);
    }

    public void sendBookingReminder(String to, String subject, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("emmadexboy4real@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p> <b>You Booked for a Room in Hotel Management System</b><br> <b>Email: </b> "+to+" <br>" +
                " <b>Password: </b> "+password+" <br> <a href=\"http://localhost:4200/\"> Click here to Login </a> </p>";
        message.setContent(htmlMsg, "text/html");
        emailSender.send(message);
    }





}
