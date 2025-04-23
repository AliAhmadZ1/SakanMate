package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.RenterDtoOut;
import com.example.sakanmate.Model.*;
import com.example.sakanmate.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


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
    private final ApartmentReviewRepository apartmentReviewRepository;
    private final OwnerRepository ownerRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public List<RenterDtoOut> getAllRenters() {
        List<Renter> renters = renterRepository.findAll();
        List<RenterDtoOut> renterDtoOuts = new ArrayList<>();

        for (Renter renter : renters) {
            RenterDtoOut renterDtoOut = new RenterDtoOut(renter.getName(), renter.getEmail(),renter.getGender());
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
        tempRenterObject.setGender(renter.getGender());
        renterRepository.save(tempRenterObject);
    }

    public void deleteRenter(Integer renterId) {
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");
        renterRepository.delete(renter);
    }

    //Ali Alshehri
    // makeReview endpoint
    public void makeReview(Integer id, Integer apartment_id, ApartmentReview apartmentReview){
        Renter renter = renterRepository.findRenterById(id);
        Apartment apartment = apartmentRepository.findApartmentById(apartment_id);
        if (renter==null)
            throw new ApiException("renter not found");
        if (apartment==null)
            throw new ApiException("apartment not found");

        apartmentReview.setRenter(renter);
        apartmentReview.setApartment(apartment);
        apartmentReviewRepository.save(apartmentReview);
    }

    public List<Renter> getRentersByGender(String gender) {
        return renterRepository.findByGenderIgnoreCase(gender);
    }




}
