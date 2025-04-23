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
}
