package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DtoOut.RenterDtoOut;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Service.RenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/renter")
public class RenterController {
    private final RenterService renterService;

    @GetMapping("/get-all-renters")
    public ResponseEntity<List<RenterDtoOut>> getAllRentersForAdmins() {
        return ResponseEntity.status(200).body(renterService.getAllRenters());
    }

    @PostMapping("/add-renter")
    public ResponseEntity<ApiResponse> addRenter(@RequestBody @Valid Renter renter) {
        renterService.addRenter(renter);
        return ResponseEntity.status(200).body(new ApiResponse("Renter added successfully."));
    }

    @PutMapping("/update-renter/{renterId}")
    public ResponseEntity<ApiResponse> updateRenter(@PathVariable Integer renterId, @Valid @RequestBody Renter renter) {
        renterService.updateRenter(renterId, renter);
        return ResponseEntity.status(200).body(new ApiResponse("Renter updated successfully."));
    }

    @DeleteMapping("/delete-renter/{renterId}")
    public ResponseEntity<ApiResponse> deleteRenter(@PathVariable Integer renterId) {
        renterService.deleteRenter(renterId);
        return ResponseEntity.status(200).body(new ApiResponse("Renter deleted successfully."));
    }

    @PostMapping("request-apartment/{renterId}/{postId}")
    public ResponseEntity<ApiResponse> requestApartment(@PathVariable Integer renterId, @PathVariable Integer postId){
        renterService.requestApartment(renterId, postId);
        return ResponseEntity.status(200).body(new ApiResponse("Request made successfully."));
    }

    @GetMapping("check-request-status/{renterId}/{requestId}")
    public ResponseEntity<String> checkRequestStatus(@PathVariable Integer renterId, @PathVariable Integer requestId){
        return ResponseEntity.status(200).body(renterService.checkRequestStatus(renterId, requestId));
    }

    @GetMapping("cancel-request/{renterId}/{requestId}")
    public ResponseEntity<ApiResponse> cancelRequest(@PathVariable Integer renterId, @PathVariable Integer requestId){
        renterService.cancelRequest(renterId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Request canceled successfully."));

    }

}
