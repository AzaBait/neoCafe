package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.entity.RegistrationCode;
import com.neobis.neoCafe.exception.RegistrationCodeNotFoundException;
import com.neobis.neoCafe.repository.RegistrationCodeRepo;
import com.neobis.neoCafe.service.RegistrationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationCodeServiceImpl implements RegistrationCodeService {

    private final RegistrationCodeRepo registrationCodeRepo;

    @Override
    public String generateRegistrationCode(String toEmail) {
        int code = (int) (Math.random() * 10000);
        String generateCode = String.format("%04d", code);
        RegistrationCode registrationCode = new RegistrationCode();
        registrationCode.setCode(generateCode);
        registrationCode.setEmail(toEmail);

        try {
            registrationCodeRepo.save(registrationCode);
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при сохранении кода регистрации в базе данных", e);
        }
        return generateCode;
    }

    @Override
    public boolean validateCode(String code) {
        try {
            RegistrationCode registrationCode = registrationCodeRepo.findByCode(code);
            return registrationCode != null;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка валидации кода: " + e.getMessage());
        }
    }

    @Override
    public RegistrationCode findByCode(String code) {
        try {
            return registrationCodeRepo.findByCode(code);
        } catch (RegistrationCodeNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
