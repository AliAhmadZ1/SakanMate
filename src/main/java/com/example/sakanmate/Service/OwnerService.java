package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DTO_In.PostDTO;
import com.example.sakanmate.Model.*;
import com.example.sakanmate.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RequestRepository requestRepository;
    private final PostRepository postRepository;
    private final AdminRepository adminRepository;
    private final ApartmentRepository apartmentRepository;
    private final ApartmentReviewRepository apartmentReviewRepository;
    private final ContractRepository contractRepository;

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

    @Transactional
    public void deleteOwner(Integer id) {
        // Check owner
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null) throw new ApiException("Owner not found.");

        // Break all relationships using bulk update
        apartmentRepository.detachAllApartmentsFromOwner(id);

        // Delete the owner
        ownerRepository.deleteById(id);
    }


    public void approveOwner(Integer ownerId, Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }

        owner.setApproved(true);
        ownerRepository.save(owner);
    }

    public void rejectOwnerByAdmin(Integer id, String reason, Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        if (reason == null) {
            throw new ApiException(reason);
        }
        owner.setApproved(false);
        owner.setRejectionReason(reason);
        ownerRepository.save(owner);
    }

    //ali
    // disable owner depend on average of rating and number of apartments
    public void disableOwner(Integer admin_id, Integer owner_id) {
        Admin admin = adminRepository.findAdminsById(admin_id);
        Owner owner = ownerRepository.findOwnerById(owner_id);
        if (admin == null)
            throw new ApiException("admin not found");
        if (owner == null)
            throw new ApiException("owner not found");
        if (!owner.isApproved())
            throw new ApiException("owner is already disabled");
        List<Apartment> apartments = apartmentRepository.findApartmentByOwnerId(owner_id);
        if (apartments.isEmpty())
            throw new ApiException("this owner doesn't have apartments");

        // the condition to disable is average rating less than 2/5 and number of apartment greater than 5
        double result = calculateAverageRatingByOwner(apartments);
        if (result > 2 && apartments.size() < 5)
            throw new ApiException("cannot disable this owner because of newly registered");

        //when meet all conditions
        owner.setApproved(false);
        owner.setRejectionReason("This owner did not meet the requirements.");
        ownerRepository.save(owner);
    }

    // helping method to calculate
    public double calculateAverageRatingByOwner(List<Apartment> apartments) {
        double averageRating = 0.0;
        double sumOfAverageRating = 0.0;
        int sumOfRating = 0;
        int counter = 0;
        int averageCounter = 0;
        for (Apartment a : apartments) {
            Set<ApartmentReview> apartmentReviews = a.getApartmentReviews();
            for (ApartmentReview ar : apartmentReviews) {
                sumOfRating = sumOfRating + ar.getRating();
                counter++;
            }
            averageRating = (double) sumOfRating / counter;
            sumOfAverageRating = sumOfAverageRating + averageRating;
            averageCounter++;
        }
        averageRating = sumOfAverageRating / averageCounter;
        return averageRating;
    }

    public Double getOwnerRevenue(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) throw new ApiException("Owner not found.");
        List<Contract> contracts = contractRepository.findApprovedContractsByOwnerId(ownerId);
        Double totalRevenue = 0.0;
        for (Contract contract : contracts) totalRevenue += contract.getTotalPrice();
        return totalRevenue;
    }

    public Integer getOwnerNumberOfRenters(Integer ownerId){
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) throw new ApiException("Owner not found.");
        List<Contract> contracts = contractRepository.findApprovedContractsByOwnerId(ownerId);
        Integer numberOfRenters = 0;
        for (Contract contract : contracts) numberOfRenters += contract.getRenters().size();
        return numberOfRenters;
    }

}
