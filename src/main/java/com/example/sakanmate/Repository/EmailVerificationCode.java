package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.EmailVerificationCodes;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailVerificationCode extends JpaRepository<EmailVerificationCodes,Integer> {

    EmailVerificationCodes findEmailVerificationCodesByCode(Integer code);

    EmailVerificationCodes findEmailVerificationCodesByEmail(String email);

    @Query("select e from EmailVerificationCodes e where e.expirationDateTime< :now-2 minute")
    List<EmailVerificationCodes> checkExpiredCodes(LocalDateTime now);
}
