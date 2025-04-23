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

    @PostMapping("/add")
    public ResponseEntity addApartment(@RequestBody @Valid Apartment apartment){
        apartmentService.addApartment(apartment);
        return ResponseEntity.status(200).body(new ApiResponse("apartment added"));
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

    @PutMapping("/apartments/{id}/approve")
    public ResponseEntity<String> approveApartment(@PathVariable Integer id) {
        apartmentService.approveApartment(id);
        return ResponseEntity.ok("Apartment approved.");
    }
}
