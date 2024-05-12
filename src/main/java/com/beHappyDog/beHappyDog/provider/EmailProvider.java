package com.beHappyDog.beHappyDog.provider;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender javaMailSender;
    private final String SUBJECT = "[행복하시개 유기견 보호소] 인증메일입니다.";

    public boolean sendCertificationMail(String email, String certificationNumber) {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email); //수신자
            messageHelper.setSubject(SUBJECT); //제목
            messageHelper.setText(htmlContent, true); //내용

            javaMailSender.send(message);

        } catch (Exception exception){
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    // 인증메일 내용 생성
    private String getCertificationMessage(String certificationNumber) {

        String certificationMessage ="";
        certificationMessage += "<h1 style='text-align: center;'>[행복하시개 유기견 보호소] 인증메일</h1>";
        certificationMessage += "<h3 style='text-align: center;'>인증코드 : <strong style='font-size: 32px; letter-spacing: 8px;'>" + certificationNumber + "</strong></h3>";

        return certificationMessage;
    }
}
