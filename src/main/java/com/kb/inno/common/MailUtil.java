package com.kb.inno.common;

import com.kb.inno.admin.VO.SendMailInfoVO;
import org.springframework.util.StringUtils;
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
import java.util.List;
import java.util.Properties;

public class MailUtil {

    public boolean sendMail(String sender, List<String> receivers, String subject, String content, MultipartFile attachment) throws Exception {

        if(!StringUtils.hasText(sender)) {
            return false;
        }

        if(receivers == null || receivers.size() == 0) {
            return false;
        }

        // SMTP 정보
        SendMailInfoVO mailInfo = SendMailInfoVO.getInfo();

        // 메일 관련 정보
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", mailInfo.getHost());
        props.setProperty("mail.smtp.port", mailInfo.getPort());
        props.setProperty("mail.smtp.auth", mailInfo.getSmtpAuth());
        //tls1.2 이슈로 false 처리
        props.setProperty("mail.smtp.starttls.enable", "false");

        System.out.println("=====mail_log====");
        System.out.println(mailInfo.getHost() + " :: " + mailInfo.getPort() + " :: " + mailInfo.getFrom() + " :: " + mailInfo.getPw());

        Session session; //Session.getDefaultInstance(props);

        if(CommonUtil.isProd(PropertiesValue.profilesActive)
            || CommonUtil.isDev(PropertiesValue.profilesActive)) {
            session = Session.getDefaultInstance(props);
        }else{
            final String finalFrom = mailInfo.getFrom();
            final String finalPw = mailInfo.getPw();
            sender = mailInfo.getFrom();
            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(finalFrom, finalPw);
                }
            });
        }

        StringBuilder sb = new StringBuilder();
        if(receivers.size() > 1) {
            for (String receiver : receivers) {
                sb.append(receiver);
                sb.append(",");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);  // 마지막 쉼표 제거
            }
        }
        else{
            sb.append(receivers.get(0));
        }

        try {
            // 메일 메시지 생성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender)); // 발신자 이메일 주소
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(sb.toString())); // 수신자 이메일 주소
            message.setSubject(subject); // 이메일 제목

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(content, "text/html; charset=UTF-8");
            textPart.setText(content);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            if(attachment != null && StringUtils.hasText(attachment.getOriginalFilename())) {
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
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
