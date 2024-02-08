package com.neobis.neoCafe.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendRegistrationEmail(String email, String registrationCode);

    void sendCodeToEmail(String email, String code) throws MessagingException;

}
