package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RequestRepository requestRepository;

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
        validateOwnerAndRequest(owner, request);

        // Check the request status.
        checkTheRequestStatus(request.getState());

        // Accept the request.
        request.setState("accepted");

        // Save the request.
        requestRepository.save(request);

        // Send a notification.
    }

    public void rejectRequest(Integer ownerId, Integer requestId) {
        // Get the owner and the request objects
        Owner owner = ownerRepository.findOwnerById(ownerId);
        Request request = requestRepository.findRequestById(requestId);

        // Validate the objects.
        validateOwnerAndRequest(owner, request);

        // Check the request status.
        checkTheRequestStatus(request.getState());

        // Reject the request.
        request.setState("rejected");

        // Save the request.
        requestRepository.save(request);
        // Send a notification.
    }

    public void checkTheRequestStatus(String status) {
        switch (status) {
            case "accepted" -> throw new ApiException("Request has been already accepted");
            case "rejected" -> throw new ApiException("Request has been rejected.");
            case "canceled" -> throw new ApiException("The request has been canceled.");
        }
    }

    public void validateOwnerAndRequest(Owner owner, Request request) {
        if (owner == null)
            throw new ApiException("Owner not found");
        if (request == null)
            throw new ApiException("Request not found");
        if (request.getPost().getOwner() != owner)
            throw new ApiException("The given request does not belong to the given owner.");
    }

    public void getOwnerPendingRequests(){

    }


}
