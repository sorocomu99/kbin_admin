package com.kb.inno.common;

import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MailUtil {

    public boolean sendMail(List<String> receivers, String subject, String content, MultipartFile attachment) {
        //TODO : 메일 정보 수정
        String host = "smtp.fmcity.com";
        String port = "587";
        final String from = "hunhee@soroweb.co.kr";
        final String password = "1q2w3e4r!@";

        if(receivers == null || receivers.size() == 0) {
            return false;
        }

        // 메일 관련 정보
        Properties props = new Properties();

        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        StringBuilder sb = new StringBuilder();
        if(receivers.size() > 1) {
            for (String receiver : receivers) {
                sb.append(receiver);
                sb.append(",");
            }
        }
        else{
            sb.append(receivers.get(0));
        }

        try {
            // 메일 메시지 생성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from)); // 발신자 이메일 주소
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(sb.toString())); // 수신자 이메일 주소
            message.setSubject(subject); // 이메일 제목

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(content, "text/html; charset=UTF-8");
            textPart.setText(content);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            if(attachment != null) {
                MimeBodyPart filePart = new MimeBodyPart();
                // 파일 데이터를 DataSource로 변환
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(attachment.getBytes());
                DataSource dataSource = new ByteArrayDataSource(byteArrayInputStream, attachment.getContentType());

                // 첨부파일 설정
                filePart.setDataHandler(new DataHandler(dataSource));
                filePart.setFileName(attachment.getOriginalFilename());

                multipart.addBodyPart(filePart);
            }

            message.setContent(multipart);
            Transport.send(message);

            return true;
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
