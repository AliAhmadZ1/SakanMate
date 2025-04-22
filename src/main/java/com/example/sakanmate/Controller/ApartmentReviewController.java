package com.example.sakanmate.Controller;

import com.example.sakanmate.DtoOut.ApartmentReviewDTO;
import com.example.sakanmate.Service.ApartmentReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ApartmentReviewController {

    private final ApartmentReviewService apartmentReviewService;

    @GetMapping("/get")
    public ResponseEntity getByApartment() {
        return ResponseEntity.ok( apartmentReviewService.getByApartment());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ApartmentReviewDTO dto) {
        apartmentReviewService.addReview(dto);
        return ResponseEntity.ok("Review submitted.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Integer id, @RequestBody ApartmentReviewDTO dto) {
        apartmentReviewService.updateReview( dto);
        return ResponseEntity.ok("Review updated successfully.");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id) {
        apartmentReviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}
