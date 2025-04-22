package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {

    Admin findAdminsById(Integer id);
}
