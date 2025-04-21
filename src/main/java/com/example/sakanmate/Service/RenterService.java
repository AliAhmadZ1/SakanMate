package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.RenterDtoOut;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RenterService {
    private final RenterRepository renterRepository;

    public List<RenterDtoOut> getAllRenters() {
        List<Renter> renters = renterRepository.findAll();
        List<RenterDtoOut> renterDtoOuts = new ArrayList<>();

        for (Renter renter : renters) {
            RenterDtoOut renterDtoOut = new RenterDtoOut(renter.getName(), renter.getEmail());
            renterDtoOuts.add(renterDtoOut);
        }

        return renterDtoOuts;
    }

    public void addRenter(Renter renter){
        renterRepository.save(renter);
    }

    public void updateRenter(Integer renterId, Renter renter){
        Renter tempRenterObject = renterRepository.findRenterById(renterId);
        if(tempRenterObject == null) throw new ApiException("Renter not found.");
        tempRenterObject.setEmail(renter.getEmail());
        tempRenterObject.setName(renter.getName());
        tempRenterObject.setPassword(renter.getPassword());
        renterRepository.save(tempRenterObject);
    }

    public void deleteRenter(Integer renterId){
        Renter renter = renterRepository.findRenterById(renterId);
        if(renter == null) throw new ApiException("Renter not found.");
        renterRepository.delete(renter);
    }
    // This is where the request get asked by the user given the post Id and the renter Id
    public void requestApartment(Integer renterId, Integer postId){
        // Check if the renter exists in the database.
        // Check if the post exists in the database.
        // Make the request (make the request object)
        // Change the status of the post.
        // Mark the request status as pending.
        // Save the objects in the database.
    }
}
