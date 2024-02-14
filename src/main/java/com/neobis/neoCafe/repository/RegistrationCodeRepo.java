package com.neobis.neoCafe.repository;

import com.neobis.neoCafe.entity.RegistrationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationCodeRepo extends JpaRepository<RegistrationCode, Long> {

    RegistrationCode findByCode(String code);

    RegistrationCode findByEmail(String email);

}
