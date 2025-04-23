package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    Post findPostById(Integer id);

    @Query("select p from Post p where p.numberOfApprovedRequests < p.apartment.max_renters")
    List<Post> findPostsByAvailability();
}
