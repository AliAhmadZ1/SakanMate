package com.example.sakanmate.DtoOut;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContractDtoOut {
    private double totalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
