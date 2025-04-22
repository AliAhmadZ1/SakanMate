package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DtoOut.EmailRequest;
import com.example.sakanmate.Service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sakan-mate/email")
@RequiredArgsConstructor
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;

    @PostMapping("/send")
    public ResponseEntity sendEmail(@RequestBody EmailRequest emailRequest){
        emailNotificationService.sendEmail(emailRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Email sent successfully"));
    }
}
