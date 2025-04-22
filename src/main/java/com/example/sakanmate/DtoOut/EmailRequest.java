package com.example.sakanmate.DtoOut;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {

    // sender , recipient , message , subject
    private String sender;
    private String recipient;
    private String message;
    private String subject;


}
