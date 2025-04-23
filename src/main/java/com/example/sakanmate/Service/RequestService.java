package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.PostRepository;
import com.example.sakanmate.Repository.RenterRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final RenterRepository renterRepository;
    private final PostRepository postRepository;
    private final OwnerRepository ownerRepository;

    public void update(){

    }

    public List<Request> getAll(){
        return requestRepository.findAll();
    }


    public void delete(){

    }

    //1-
    // This is where the request get asked by the user given the post id and the renter id
    public void requestApartment(Integer renterId, Integer postId, int months) {
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the post exists in the database.
        Post post = postRepository.findPostById(postId);
        if (post == null) throw new ApiException("Post not found.");

        // Check the post status.
        switch (post.getStatus()) {
            case "pending" -> throw new ApiException("Post has not been approved by an admin.");
            case "canceled" -> throw new ApiException("Post has been canceled by the owner.");
            case "rented" -> throw new ApiException("This apartment has been rented.");
        }

        // Check the months
        if (months < 1) throw new ApiException("The months need to greater than 1.");

        // Make the request (make the request object) and mark the request status as pending.
        Request request = new Request(null, "pending", LocalDateTime.now(), months, renter, post);
        renter.getRequests().add(request);

        // Save the objects in the database.
        requestRepository.save(request);
        renterRepository.save(renter);
    }

    // 2-
    public String checkRequestStatus(Integer renterId, Integer requestId) {
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the post exists in the database.
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("Request not found.");

        // Check if the request was made by the renter.
        if (request.getRenter() != renter) throw new ApiException("Request and renter do not match.");
        return request.getState();
    }

    // 3-
    public void cancelRequest(Integer renterId, Integer requestId) {
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the post exists in the database.
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("Request not found.");

        // Change the state and save the request.
        request.setState("canceled");
        requestRepository.save(request);
    }

    public void acceptRequest(Integer ownerId, Integer requestId) {
        // Get the owner and the request objects
        Owner owner = ownerRepository.findOwnerById(ownerId);
        Request request = requestRepository.findRequestById(requestId);

        // Validate the objects.
        if (owner == null)
            throw new ApiException("Owner not found");
        if (request == null)
            throw new ApiException("Request not found");
        if (request.getPost().getOwner() != owner)
            throw new ApiException("The given request does not belong to the given owner.");

        // Check the request status.
        switch (request.getState()) {
            case "accepted" -> throw new ApiException("Request has been already accepted");
            case "rejected" -> throw new ApiException("Request has been rejected.");
            case "canceled" -> throw new ApiException("The request has been canceled.");
        }

        //Get the post
        Post post = request.getPost();

        // Check if the apartment is full
        if (post.getApartment().getNumber_of_remaining() < 1)
            throw new ApiException("The apartment is full.");

        // Accept the request.
        request.setState("accepted");


        // Decrease the number of remaining.
        post.getApartment().setNumber_of_remaining(request.getPost().getApartment().getNumber_of_remaining()-1);

        // Increase the number of approved requests
        post.setNumberOfApprovedRequests(post.getNumberOfApprovedRequests()+1);

        // Save the request.
        requestRepository.save(request);

        // Send a notification.
    }

    public void rejectRequest(Integer ownerId, Integer requestId) {
        // Get the owner and the request objects
        Owner owner = ownerRepository.findOwnerById(ownerId);
        Request request = requestRepository.findRequestById(requestId);

        // Validate the objects.
        if (owner == null)
            throw new ApiException("Owner not found");
        if (request == null)
            throw new ApiException("Request not found");
        if (request.getPost().getOwner() != owner)
            throw new ApiException("The given request does not belong to the given owner.");

        // Check the request status.
        switch (request.getState()) {
            case "accepted" -> throw new ApiException("Request has been already accepted");
            case "rejected" -> throw new ApiException("Request has been rejected.");
            case "canceled" -> throw new ApiException("The request has been canceled.");
        }

        // Reject the request.
        request.setState("rejected");

        // Save the request.
        requestRepository.save(request);
        // Send a notification.
    }


    public List<Request> getOwnerPendingRequests(Integer ownerId) {
        // Get the owner and check if the owner in the database.
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) throw new ApiException("Owner not found");

        // Get the pending requests.
        List<Request> pendingRequests = requestRepository.findRequestsByOwnerAndStatus(ownerId);

        // Check if the owner has pending requests.
        if (pendingRequests.isEmpty()) throw new ApiException("There are no pending requests.");

        // Return the requests.
        return pendingRequests;
    }

}

