package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    Request findRequestById(Integer requestId);

    @Query("select r from Request r where r.post.owner.id = ?1 and r.state = 'pending'")
    List<Request> findRequestsByOwnerAndStatus(Integer ownerId);
}
