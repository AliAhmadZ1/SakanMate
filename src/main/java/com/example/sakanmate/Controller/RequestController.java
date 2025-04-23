package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.RequestRepository;
import com.example.sakanmate.Service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/renter")
public class RequestController {
    private final RequestService requestService;
    @PostMapping("/request-apartment/{renterId}/{postId}")
    public ResponseEntity<ApiResponse> requestApartment(@PathVariable Integer renterId, @PathVariable Integer postId, @RequestBody int months){
        requestService.requestApartment(renterId, postId, months);
        return ResponseEntity.status(200).body(new ApiResponse("Request made successfully."));
    }

    @GetMapping("/check-request-status/{renterId}/{requestId}")
    public ResponseEntity<String> checkRequestStatus(@PathVariable Integer renterId, @PathVariable Integer requestId){
        return ResponseEntity.status(200).body(requestService.checkRequestStatus(renterId, requestId));
    }

    @PutMapping("/cancel-request/{renterId}/{requestId}")
    public ResponseEntity<ApiResponse> cancelRequest(@PathVariable Integer renterId, @PathVariable Integer requestId){
        requestService.cancelRequest(renterId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request canceled successfully."));

    }

    @PutMapping("/accept-request/{ownerId}/{requestId}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable Integer ownerId, @PathVariable Integer requestId) {
        requestService.acceptRequest(ownerId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request Accepted."));
    }

    @PutMapping("/reject-request/{ownerId}/{requestId}")
    public ResponseEntity<ApiResponse> rejectRequest(@PathVariable Integer ownerId, @PathVariable Integer requestId) {
        requestService.rejectRequest(ownerId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request Rejected."));
    }

    @GetMapping("/get-owner-pending-requests/{ownerId}")
    public ResponseEntity<List<Request>> getOwnerPendingRequests(@PathVariable Integer ownerId){
        return ResponseEntity.status(200).body(requestService.getOwnerPendingRequests(ownerId));
    }
}
