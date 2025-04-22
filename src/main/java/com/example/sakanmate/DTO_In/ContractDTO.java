package com.example.sakanmate.DTO_In;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContractDTO {

    private Integer apartment_id;
    @Positive
    @NotNull(message = "The total price can not be null.")
    private double totalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
