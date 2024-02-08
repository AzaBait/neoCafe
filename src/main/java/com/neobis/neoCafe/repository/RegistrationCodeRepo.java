package com.neobis.neoCafe.repository;

import com.neobis.neoCafe.entity.RegistrationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationCodeRepo extends JpaRepository<RegistrationCode, Long> {

    RegistrationCode findByCode(String code);
}
