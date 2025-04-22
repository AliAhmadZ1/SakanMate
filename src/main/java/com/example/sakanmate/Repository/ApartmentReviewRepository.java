package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.ApartmentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ApartmentReviewRepository extends JpaRepository<ApartmentReview,Integer> {

    ApartmentReview findApartmentReviewById(Integer id);
    List<ApartmentReview> findApartmentReviewByApartmentId(Integer apartmentId);

}
