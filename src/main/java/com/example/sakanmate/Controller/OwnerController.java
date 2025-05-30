package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    // Endpoint 16
    //khadija
    @PutMapping("/owners/{id}/approve/{adminId}")
    public ResponseEntity<String> approveOwner(@PathVariable Integer id,@PathVariable Integer adminId) {
        ownerService.approveOwner(id,adminId);
        return ResponseEntity.ok("Owner approved.");
    }

    // Endpoint 17
    //khadija
    @PutMapping("/reject-by-admin/{id}/{adminId}")
    public ResponseEntity<String> rejectOwnerByAdmin(@PathVariable Integer id, @RequestBody String reason,@PathVariable Integer adminId) {
        ownerService.rejectOwnerByAdmin(id, reason,adminId);
        return ResponseEntity.ok("Owner rejected based on license.");
    }

    // Endpoint 18
    //ali
    @PutMapping("/disable-owner/admin/{admin_id}/owner/{owner_id}")
    public ResponseEntity disableOwner(@PathVariable Integer admin_id, @PathVariable Integer owner_id){
        ownerService.disableOwner(admin_id, owner_id);
        return ResponseEntity.status(200).body(new ApiResponse("Owner disabled"));
    }

    // Endpoint 32
    //ali
    @GetMapping("/get-owner-revenue/{ownerId}")
    public ResponseEntity<ApiResponse> getOwnerTotalRevenue(@PathVariable Integer ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The owner total revenue is: " + ownerService.getOwnerRevenue(ownerId) + "."));
    }

    // Endpoint 33
    //ali
    @GetMapping("/get-owner-number-of-renters/{ownerId}")
    public ResponseEntity<ApiResponse> getOwnerNumberOfRenters(@PathVariable Integer ownerId){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("The owner has " + ownerService.getOwnerNumberOfRenters(ownerId) + " renters." ));
    }


}
