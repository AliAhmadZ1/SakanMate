package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
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
    public ResponseEntity addPost(@RequestBody @Valid Post post){
        postService.addPost(post);
        return ResponseEntity.status(200).body(new ApiResponse("post added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Integer id, @RequestBody@Valid Post post){
        postService.updatePost(id, post);
        return ResponseEntity.status(200).body(new ApiResponse("post updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Integer id){
        postService.deletePost(id);
        return ResponseEntity.status(200).body(new ApiResponse("post deleted"));
    }

}
