package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.entity.RegistrationCode;
import com.neobis.neoCafe.service.EmailService;
import com.neobis.neoCafe.service.RegistrationCodeService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final RegistrationCodeService registrationCodeService;

    @Override
    public void sendRegistrationEmail(String email, String registrationCode) {
        RegistrationCode code = new RegistrationCode();
        code.setEmail(email);
        code.setCode(registrationCode);
        try {
            sendCodeToEmail(email, registrationCode);
        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка при отправке письма с кодом регистрации на " + email, e);
        }
    }
    @Override
    public void sendCodeToEmail(String email, String code) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(email);
        messageHelper.setSubject("Закончите регистрацию");
        messageHelper.setText("Ваш код регистрации:\n\n" + code);
        javaMailSender.send(mimeMessage);
    }
}
