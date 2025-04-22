package com.example.sakanmate.DTO_In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class AdminDTO_In {
    @Email
    private String email;

    @NotEmpty
    private String password;
}
