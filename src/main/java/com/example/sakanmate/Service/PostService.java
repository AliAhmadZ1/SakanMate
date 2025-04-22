package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public void addPost(Post post){
        post.setPostDate(LocalDate.now());
        postRepository.save(post);
    }

    public void updatePost(Integer id, Post post){
        Post oldPost = postRepository.findPostById(id);
        if (oldPost==null)
            throw new ApiException("Post not found");
        oldPost.setStatus(post.getStatus());
        postRepository.save(oldPost);
    }

    public void deletePost(Integer id){
        Post post = postRepository.findPostById(id);
        if (post==null)
            throw new ApiException("post not found");

        postRepository.delete(post);
    }
}
