package com.example.sakanmate.DTO_In;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostDTO {

    @Pattern(regexp = "approved|pending|canceled|rented")
    private String status;
    private LocalDate postDate;

}
