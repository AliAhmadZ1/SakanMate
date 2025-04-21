package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {
    Renter findRenterById(Integer renterId);
}
