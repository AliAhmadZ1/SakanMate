package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ApartmentReviewDTO;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.ApartmentReview;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ApartmentReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ApartmentReviewService {


    private ApartmentReviewRepository repo;
    private AdminRepository adminRepository;

    public List<ApartmentReview> getByApartment() {
        return repo.findAll();
    }

    public void addReview(ApartmentReviewDTO dto) {
        ApartmentReview review = new ApartmentReview();
        review.setRating(dto.getRating());
        review.setComment(dto.getDescription());
        repo.save(review);
    }

    public void updateReview(ApartmentReviewDTO dto) {
        ApartmentReview review = repo.findApartmentReviewById(dto.getRenterId());
        if (review == null) {
            throw new ArithmeticException();
        }

        review.setRating(dto.getRating());
        review.setComment(dto.getDescription());

        repo.save(review);
    }

    public void deleteReview(Integer id) {
        repo.deleteById(id);
    }


    public void deleteReviewById(Integer reviewId, Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin==null){
            throw new ApiException("Admin not found");
        }

        ApartmentReview review = repo.findApartmentReviewById(reviewId);
        if (review==null){
            throw new ApiException("Review not found");
        }

        repo.delete(review);
    }
}
