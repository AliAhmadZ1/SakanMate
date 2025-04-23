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
}
