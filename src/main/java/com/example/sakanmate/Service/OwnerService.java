package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.PostRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RequestRepository requestRepository;
    private final PostRepository postRepository;

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public void addOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id, Owner owner) {
        Owner oldOwner = ownerRepository.findOwnerById(id);
        if (oldOwner == null)
            throw new ApiException("Owner not found");

        oldOwner.setEmail(owner.getEmail());
        oldOwner.setName(owner.getName());
        oldOwner.setPassword(owner.getPassword());
        ownerRepository.save(oldOwner);
    }

    public void deleteOwner(Integer id) {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null)
            throw new ApiException("Owner not found");

        ownerRepository.delete(owner);
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

    public void approveOwner(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner==null){
            throw new RuntimeException("Owner not found");
        }

        owner.setApproved(true);
        ownerRepository.save(owner);
    }

    public void rejectOwnerByAdmin(Integer id, String reason) {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner==null){
            throw new RuntimeException("Owner not found");
        }

        owner.setApproved(false);
        owner.setRejectionReason(reason);
        ownerRepository.save(owner);
    }


}
