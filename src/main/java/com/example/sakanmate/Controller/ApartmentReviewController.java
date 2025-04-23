package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DtoOut.ApartmentReviewDTO;
import com.example.sakanmate.Service.ApartmentReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/review")
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

    @PutMapping("/up-vote/{renter_id}/review/{review_id}")
    public ResponseEntity upVoteReview(@PathVariable Integer renter_id, @PathVariable Integer review_id){
        apartmentReviewService.upVoteReview(renter_id, review_id);
        return ResponseEntity.status(200).body(new ApiResponse("up vote review"));
    }

    @PutMapping("/down-vote/{renter_id}/review/{review_id}")
    public ResponseEntity downVoteReview(@PathVariable Integer renter_id, @PathVariable Integer review_id){
        apartmentReviewService.downVoteReview(renter_id, review_id);
        return ResponseEntity.status(200).body(new ApiResponse("down vote review"));
    }
}
