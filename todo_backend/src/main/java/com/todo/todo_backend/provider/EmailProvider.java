package com.todo.todo_backend.provider;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {

    private final JavaMailSender javaMailSender;
    private final String SUBJECT = "[EMPL] 인증 메일 입니다.";

    public boolean sendCertificationMail(String email, String certificationNumber) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            String htmlContent = getCertificationMessage(certificationNumber);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String getCertificationMessage(String certificationNumber) {
        String certificationMessage = "";
        certificationMessage += "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #fff;\">";
        certificationMessage += "<h2 style=\"text-align: center; color: #007bff;\">이메일 인증 요청</h2>";
        certificationMessage += "<p style=\"text-align: center; color: #666; font-size: 16px;\">WeRO by (주)CKO네트웍스</p>";
        certificationMessage += "<p style=\"text-align: center; color: #666; font-size: 16px;\">인증 번호:</p>";
        certificationMessage += "<p style=\"text-align: center; font-size: 24px; color: #007bff;\"><strong>"
                + certificationNumber + "</strong></p>";
        // certificationMessage += "<p style=\"text-align: center; color: #666;
        // font-size: 16px;\">이 번호를 사용하여 본인 인증을 완료해주세요.</p>";
        // certificationMessage += "<p style=\"text-align: center; color: #666;
        // font-size: 16px;\">만약 이 번호 요청을 하지 않았다면 이 이메일을 무시하셔도 됩니다.</p>";
        // certificationMessage += "<p style=\"text-align: center; color: #666;
        // font-size: 16px;\">감사합니다,<br> For you and For us WeRo (주)CKO네트웍스</p>";
        certificationMessage += "</div>";

        return certificationMessage;
    }

}
