package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.RenterDtoOut;
import com.example.sakanmate.Model.*;
import com.example.sakanmate.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RenterService {
    private final RenterRepository renterRepository;
    private final PostRepository postRepository;
    private final RequestRepository requestRepository;
    private final ContractRepository contractRepository;
    private final ApartmentRepository apartmentRepository;

    public List<RenterDtoOut> getAllRenters() {
        List<Renter> renters = renterRepository.findAll();
        List<RenterDtoOut> renterDtoOuts = new ArrayList<>();

        for (Renter renter : renters) {
            RenterDtoOut renterDtoOut = new RenterDtoOut(renter.getName(), renter.getEmail());
            renterDtoOuts.add(renterDtoOut);
        }

        return renterDtoOuts;
    }

    public void addRenter(Renter renter) {
        renterRepository.save(renter);
    }

    public void updateRenter(Integer renterId, Renter renter) {
        Renter tempRenterObject = renterRepository.findRenterById(renterId);
        if (tempRenterObject == null) throw new ApiException("Renter not found.");
        tempRenterObject.setEmail(renter.getEmail());
        tempRenterObject.setName(renter.getName());
        tempRenterObject.setPassword(renter.getPassword());
        renterRepository.save(tempRenterObject);
    }

    public void deleteRenter(Integer renterId) {
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");
        renterRepository.delete(renter);
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
        if(months < 1) throw new ApiException("The months need to greater than 1.");

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
        if(request.getRenter() != renter) throw new ApiException("Request and renter do not match.");
        return request.getState();
    }

    // 3-
    public void cancelRequest(Integer renterId, Integer requestId){
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

    public void acceptContract(Integer renterId, Integer contractId){
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the contract exists in the database.
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) throw new ApiException("Contract not found.");

        // Check if the contract belong to the renter ************8

        // Add the renter to the contract renters * renter accepting the contract.
        contract.getRenters().add(renter);
    }

    public void fileAComplaint(Integer renterId, Integer apartmentId, String title, String description){
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the contract exists in the database.
        Apartment apartment = apartmentRepository.findApartmentById(apartmentId);
        if (apartment == null) throw new ApiException("Apartment not found.");

        // Check if the apartment does not belong to the renter
        if(!apartment.getContract().getRenters().contains(renter)) throw new ApiException("The apartment does not belong to the renter.");

        // Make the complaint
        Complaint complaint = new Complaint(null, title, description, null, renter, apartment);
    }
}
