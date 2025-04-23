package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Service.ApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sakan-mate/apartment")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(apartmentService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateApartment(@PathVariable Integer id, @RequestBody@Valid Apartment apartment){
        apartmentService.updateApartment(id, apartment);
        return ResponseEntity.status(200).body(new ApiResponse("apartment updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteApartment(@PathVariable Integer id){
        apartmentService.deleteApartment(id);
        return ResponseEntity.status(200).body(new ApiResponse("apartment deleted"));
    }

    // Endpoint 1
    //khadija
    @PutMapping("/approve/{id}/{adminId}")
    public ResponseEntity<String> approveApartment(@PathVariable Integer id,@PathVariable Integer adminId) {
        apartmentService.approveApartment(id,adminId);
        return ResponseEntity.ok("Apartment approved.");
    }

    // Endpoint 2
    //khadija
    @PutMapping("/reject-apartment/{id}/{adminId}")
    public ResponseEntity<String> rejectApartment(@PathVariable Integer id, @RequestBody String reason,@PathVariable Integer adminId) {
        apartmentService.rejectApartment(id, reason, adminId);
        return ResponseEntity.ok("Apartment rejected.");
    }

    // Endpoint 3
    //ali
    @PostMapping("/add-apartment-to-sakanmate/{id}")
    public ResponseEntity addApartmentToSakanMate(@PathVariable Integer id, @RequestBody Apartment apartment){
        apartmentService.addApartmentToSakanMate(id, apartment);
        return ResponseEntity.status(200).body(new ApiResponse("new Apartment added"));
    }

}
