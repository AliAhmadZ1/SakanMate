package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ApartmentReviewDTO;
import com.example.sakanmate.Model.ApartmentReview;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Repository.ApartmentReviewRepository;
import com.example.sakanmate.Repository.RenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApartmentReviewService {


    private final RenterRepository renterRepository;
    private final ApartmentReviewRepository apartmentReviewRepository;
    private ApartmentReviewRepository repo;

    public List<ApartmentReview> getByApartment() {
        return repo.findAll();
    }

    public void addReview(ApartmentReviewDTO dto) {
        ApartmentReview review = new ApartmentReview();
        review.setRating(dto.getRating());
        review.setDescription(dto.getDescription());
        repo.save(review);
    }

    public void updateReview(ApartmentReviewDTO dto) {
        ApartmentReview review = repo.findApartmentReviewById(dto.getRenterId());
        if (review == null) {
            throw new ArithmeticException();
        }

        review.setRating(dto.getRating());
        review.setDescription(dto.getDescription());

        repo.save(review);
    }

    public void deleteReview(Integer id) {
        repo.deleteById(id);
    }

    //ali
    public void upVoteReview(Integer renter_id, Integer review_id) {
        Renter renter = renterRepository.findRenterById(renter_id);
        ApartmentReview apartmentReview = apartmentReviewRepository.findApartmentReviewById(review_id);
        if (renter == null)
            throw new ApiException("renter not found");
        if (apartmentReview == null)
            throw new ApiException("apartment review not found");
        apartmentReview.setUp_vote(apartmentReview.getUp_vote() + 1);
        apartmentReviewRepository.save(apartmentReview);
    }

    //ali
    public void downVoteReview(Integer renter_id, Integer review_id) {
        Renter renter = renterRepository.findRenterById(renter_id);
        ApartmentReview apartmentReview = apartmentReviewRepository.findApartmentReviewById(review_id);
        if (renter == null)
            throw new ApiException("renter not found");
        if (apartmentReview == null)
            throw new ApiException("apartment review not found");
        apartmentReview.setDown_vote(apartmentReview.getDown_vote() + 1);
        apartmentReviewRepository.save(apartmentReview);
    }
}
