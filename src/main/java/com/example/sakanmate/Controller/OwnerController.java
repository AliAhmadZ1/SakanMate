package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sakan-mate/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/get")
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.status(200).body(ownerService.getAllOwners());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addOwner(@RequestBody @Valid Owner owner) {
        ownerService.addOwner(owner);
        return ResponseEntity.status(200).body(new ApiResponse("owner added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateOwner(@PathVariable Integer id, @RequestBody @Valid Owner owner) {
        ownerService.updateOwner(id, owner);
        return ResponseEntity.status(200).body(new ApiResponse("owner updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteOwner(@PathVariable Integer id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.status(200).body(new ApiResponse("owner deleted"));
    }



    @PutMapping("/owners/{id}/approve")
    public ResponseEntity<String> approveOwner(@PathVariable Integer id) {
        ownerService.approveOwner(id);
        return ResponseEntity.ok("Owner approved.");
    }

    @PutMapping("/reject-by-admin/{id}")
    public ResponseEntity<String> rejectOwnerByAdmin(@PathVariable Integer id, @RequestBody String reason) {
        ownerService.rejectOwnerByAdmin(id, reason);
        return ResponseEntity.ok("Owner rejected based on license.");
    }



}
