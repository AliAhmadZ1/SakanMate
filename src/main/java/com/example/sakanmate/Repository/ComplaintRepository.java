package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    Complaint findComplaintById(Integer id);
}
