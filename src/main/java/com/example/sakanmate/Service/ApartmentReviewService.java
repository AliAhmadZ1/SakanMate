package com.example.sakanmate.Service;

import com.example.sakanmate.DtoOut.ApartmentReviewDTO;
import com.example.sakanmate.Model.ApartmentReview;
import com.example.sakanmate.Repository.ApartmentReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ApartmentReviewService {


    private ApartmentReviewRepository repo;

    public List<ApartmentReview> getByApartment(Integer apartmentId) {
        return repo.findByApartmentId(apartmentId);
    }

    public void addReview(ApartmentReviewDTO dto) {
        ApartmentReview review = new ApartmentReview();
        review.setRating(dto.getRating());
        review.setDescription(dto.getDescription());
        review.setApartmentId(dto.getApartmentId());
        review.setRenterId(dto.getRenterId());
        repo.save(review);
    }

    public void updateReview( ApartmentReviewDTO dto) {
        ApartmentReview review = repo.findByApartmentId(dto.getRenterId());



        review.setRating(dto.getRating());
        review.setDescription(dto.getDescription());
        review.setApartmentId(dto.getApartmentId());
        review.setRenterId(dto.getRenterId());

        repo.save(review);
    }

    public void deleteReview(Integer id) {
        repo.deleteById(id);
    }
}
