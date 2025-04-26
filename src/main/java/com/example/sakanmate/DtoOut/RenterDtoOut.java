package com.example.sakanmate.DtoOut;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RenterDtoOut {
    private String name;
    private String email;
    @Pattern(regexp = "^(?i)(male|female)$", message = "Gender must be 'male' or 'female'")
    private String gender;
    private Boolean emailVerfied;

}
