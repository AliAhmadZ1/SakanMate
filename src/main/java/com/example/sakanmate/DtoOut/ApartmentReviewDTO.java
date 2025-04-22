package com.example.sakanmate.DtoOut;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class ApartmentReviewDTO {
    @Min(1)
    @Max(5)
    private Integer rating;


    private String description;


    private Integer apartmentId;


    private Integer renterId;
}
