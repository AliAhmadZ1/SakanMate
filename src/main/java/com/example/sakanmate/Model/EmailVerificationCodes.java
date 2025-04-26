package com.example.sakanmate.Model;

import jakarta.annotation.security.DenyAll;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmailVerificationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(columnDefinition = "varchar(100) not null")
    private String email;
    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer code;
    @NotNull
    @Column(columnDefinition = "timestamp not null")
    private LocalDateTime expirationDateTime;

}
