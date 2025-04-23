package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DTO_In.PostDTO;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sakan-mate/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/get")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(postService.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity addPost(@RequestBody @Valid PostDTO postDTO){
        postService.addPost(postDTO);
        return ResponseEntity.status(200).body(new ApiResponse("post added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Integer id, @RequestBody@Valid PostDTO postDTO){
        postService.updatePost(id, postDTO);
        return ResponseEntity.status(200).body(new ApiResponse("post updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Integer id){
        postService.deletePost(id);
        return ResponseEntity.status(200).body(new ApiResponse("post deleted"));
    }

    @PutMapping("/posts/{postId}/approve/{adminId}")
    public ResponseEntity<String> approvePost(@PathVariable Integer postId, @PathVariable Integer adminId) {
        postService.approvePost(postId, adminId);
        return ResponseEntity.ok("Post approved by admin.");
    }

    @PutMapping("/cancel-posts/{id}")
    public ResponseEntity<String> cancelPost(@PathVariable Integer id) {
        postService.cancelPost(id);
        return ResponseEntity.ok("Post canceled.");
    }

    @PutMapping("/posts/{id}/{adminId}")
    public ResponseEntity<String> rejectPost(@PathVariable Integer id, @RequestBody String reason, @PathVariable Integer adminId) {
        postService.rejectPost(id, reason, adminId);
        return ResponseEntity.ok("Post rejected successfully.");
    }


}
