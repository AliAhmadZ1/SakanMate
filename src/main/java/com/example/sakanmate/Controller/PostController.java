package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DTO_In.PostDTO;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/sakan-mate/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get")
    public ResponseEntity getAll() {
        return ResponseEntity.status(200).body(postService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Integer id, @RequestBody @Valid PostDTO postDTO) {
        postService.updatePost(id, postDTO);
        return ResponseEntity.status(200).body(new ApiResponse("post updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.status(200).body(new ApiResponse("post deleted"));
    }

    @PutMapping("/posts/{postId}/approve/{adminId}")
    public ResponseEntity<String> approvePost(@PathVariable Integer postId, @PathVariable Integer adminId) {
        postService.approveAndPublishPost(postId, adminId);
        return ResponseEntity.ok("Post approved by admin.");
    }

    @PutMapping("/cancel-posts/{id}/{ownerId}")
    public ResponseEntity<String> cancelPost(@PathVariable Integer id, @PathVariable Integer ownerId) {
        postService.cancelPost(id, ownerId);
        return ResponseEntity.ok("Post canceled.");
    }

    @PutMapping("/posts/{id}/{adminId}")
    public ResponseEntity<String> rejectPost(@PathVariable Integer id, @RequestBody String reason, @PathVariable Integer adminId) {
        postService.rejectPost(id, reason, adminId);
        return ResponseEntity.ok("Post rejected successfully.");
    }

    @PostMapping("create-post/{id}/apartment/{apartment_id}")
    public ResponseEntity createPost(@PathVariable Integer id, @PathVariable Integer apartment_id) {
        postService.createPost(id, apartment_id);
        return ResponseEntity.status(200).body(new ApiResponse("new post created"));
    }
}
