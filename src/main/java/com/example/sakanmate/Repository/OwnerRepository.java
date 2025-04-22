package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {

    Owner findOwnerById(Integer id);
    //Owner findByRe
}
