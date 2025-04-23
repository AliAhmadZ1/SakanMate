package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {
    Renter findRenterById(Integer renterId);
    List<Renter> findByGenderIgnoreCase(String gender);
}
