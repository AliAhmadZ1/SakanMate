package com.example.sakanmate.DtoOut;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminDTO_Out {
    private String name;
    private String email;
    private String type;

}
