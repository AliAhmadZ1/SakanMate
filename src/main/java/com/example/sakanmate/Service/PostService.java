package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DTO_In.PostDTO;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Repository.ApartmentRepository;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ApartmentRepository apartmentRepository;
    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;

    public List<Post> getAll() {
        return postRepository.findAll();
    }


//    public void addPost(PostDTO postDTO){
//        Apartment apartment = apartmentRepository.findApartmentById(postDTO.getApartment_id());
//        if (apartment==null)
//            throw new ApiException("apartment not found");
//        Owner owner = ownerRepository.findOwnerById(apartment.getOwner().getId());
//        if (owner==null)
//            throw new ApiException("owner don't found");
//        postDTO.setPostDate(LocalDate.now());
//        Post post = new Post(null,postDTO.getStatus(),postDTO.getPostDate(),0,false,LocalDateTime.now(),"",null,apartment,owner,null);
//        postRepository.save(post);
//    }

    public void updatePost(Integer id, PostDTO postDTO){
        Post oldPost = postRepository.findPostById(id);
        if (oldPost == null)
            throw new ApiException("Post not found");

        oldPost.setStatus(postDTO.getStatus());
        postRepository.save(oldPost);
    }

    public void deletePost(Integer id) {
        Post post = postRepository.findPostById(id);
        if (post == null)
            throw new ApiException("post not found");

        postRepository.delete(post);
    }

    //Khadija
    public void approveAndPublishPost(Integer postId, Integer adminId) {
        Post post = postRepository.findPostById(postId);
        if (post==null){
            throw new ApiException("Post not found");
        }

        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin==null){
            throw new ApiException("Admin not found");
        }

        if (post.isApproved())
            throw new ApiException("is approved already");

        post.setStatus("approved");
        post.setApproved(true);
        post.setApprovedDate(LocalDateTime.now());
        post.setAdmin(admin);
        try {
            postRepository.save(post);
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            admin.getPost().add(post);
        } catch (ConcurrentModificationException ignored) {
        }
        try {
            adminRepository.save(admin);
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void cancelPost(Integer postId, Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        Post post = postRepository.findPostById(postId);
        if (post==null){
            throw new ApiException("Post not found");
        }

        post.setStatus("canceled");
        postRepository.save(post);
    }

    public void rejectPost(Integer postId, String reason, Integer adminId) {
        Post post = postRepository.findPostById(postId);
        if (post==null){
            throw new ApiException("Post not found");
        }

        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin==null){
            throw new ApiException("Admin not found");
        }
        if (reason==null){
            throw new ApiException(reason);
        }

        post.setApproved(false);
        post.setStatus("rejected");
        post.setRejectionReason(reason);
        post.setApprovedDate(LocalDateTime.now());
        post.setAdmin(admin);

        postRepository.save(post);
    }

    //ali
    public void createPost(Integer ownerId, Integer apartment_id) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        Apartment apartment = apartmentRepository.findApartmentById(apartment_id);
        if (owner == null)
            throw new ApiException("owner not found");
        if (apartment == null)
            throw new ApiException("apartment not found");
        Post post = new Post(null, "pending", LocalDateTime.now(), 0, false, null, null, null, apartment, owner, null);
        postRepository.save(post);

        ownerRepository.save(owner);
        apartmentRepository.save(apartment);
    }

    public List<Post> getNewPosts(){
        return postRepository.findPostsByAvailability();
    }

}
