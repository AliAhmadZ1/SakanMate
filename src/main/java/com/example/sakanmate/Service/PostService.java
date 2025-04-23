package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DTO_In.PostDTO;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Repository.ApartmentRepository;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ApartmentRepository apartmentRepository;
    private final OwnerRepository ownerRepository;

    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public void addPost(PostDTO postDTO){
        Apartment apartment = apartmentRepository.findApartmentById(postDTO.getApartment_id());
        if (apartment==null)
            throw new ApiException("apartment not found");
        Owner owner = ownerRepository.findOwnerById(apartment.getOwner().getId());
        postDTO.setPostDate(LocalDate.now());
        Post post = new Post(null,postDTO.getStatus(),postDTO.getPostDate(),null,apartment,owner,null);
        postRepository.save(post);
    }

    public void updatePost(Integer id, PostDTO postDTO){
        Post oldPost = postRepository.findPostById(id);
        if (oldPost==null)
            throw new ApiException("Post not found");

        oldPost.setStatus(postDTO.getStatus());
        postRepository.save(oldPost);
    }

    public void deletePost(Integer id){
        Post post = postRepository.findPostById(id);
        if (post==null)
            throw new ApiException("post not found");

        postRepository.delete(post);
    }
}
