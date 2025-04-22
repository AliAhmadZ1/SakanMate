package com.example.sakanmate.DtoOut;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComplaintDTO {

    private String title;


    private String description;


    private Integer apartmentId;


    private Integer renterId;
}
