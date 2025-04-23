package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Owner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Owner findOwnerById(Integer id);

    @Modifying
    @Query("DELETE FROM Owner o WHERE o.id = :id")
    void deleteById(@Param("id") Integer id);


}
