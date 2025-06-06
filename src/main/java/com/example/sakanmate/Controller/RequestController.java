package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.RequestRepository;
import com.example.sakanmate.Service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/request")
public class RequestController {
    private final RequestService requestService;

    // Endpoint 24
    //ayman
    @PostMapping("/request-apartment/{renterId}/{postId}")
    public ResponseEntity<ApiResponse> requestApartment(@PathVariable Integer renterId, @PathVariable Integer postId, @RequestParam Integer months){
        requestService.requestApartment(renterId, postId, months);
        return ResponseEntity.status(200).body(new ApiResponse("Request made successfully."));
    }


    // Endpoint 25
    //ayman
    @GetMapping("/check-request-status/{renterId}/{requestId}")
    public ResponseEntity<ApiResponse> checkRequestStatus(@PathVariable Integer renterId, @PathVariable Integer requestId){
        return ResponseEntity.status(200).body(new ApiResponse(requestService.checkRequestStatus(renterId, requestId)));
    }

    // Endpoint 26
    //ayman
    @PutMapping("/cancel-request/{renterId}/{requestId}")
    public ResponseEntity<ApiResponse> cancelRequest(@PathVariable Integer renterId, @PathVariable Integer requestId){
        requestService.cancelRequest(renterId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request canceled successfully."));

    }

    // Endpoint 27
    //ayman
    @PutMapping("/accept-request/{ownerId}/{requestId}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable Integer ownerId, @PathVariable Integer requestId) {
        requestService.acceptRequest(ownerId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request Accepted."));
    }

    // Endpoint 28
    //ayman
    @PutMapping("/reject-request/{ownerId}/{requestId}")
    public ResponseEntity<ApiResponse> rejectRequest(@PathVariable Integer ownerId, @PathVariable Integer requestId) {
        requestService.rejectRequest(ownerId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request Rejected."));
    }

    // Endpoint 29
    //ayman
    @GetMapping("/get-owner-pending-requests/{ownerId}")
    public ResponseEntity<List<Request>> getOwnerPendingRequests(@PathVariable Integer ownerId){
        return ResponseEntity.status(200).body(requestService.getOwnerPendingRequests(ownerId));
    }
}
