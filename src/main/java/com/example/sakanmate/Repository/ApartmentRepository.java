package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Integer> {

    Apartment findApartmentById(Integer id);

    List<Apartment> findApartmentByOwnerId(Integer owner_id);
}
