package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ApartmentRepository;
import com.example.sakanmate.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;

    public List<Apartment> getAll(){
        return apartmentRepository.findAll();
    }

    public void updateApartment(Integer id,Apartment apartment){
        Apartment oldApartment = apartmentRepository.findApartmentById(id);
        if (oldApartment==null)
            throw new ApiException("Apartment not found");

        oldApartment.setTitle(apartment.getTitle());
        oldApartment.setDescription(apartment.getDescription());
        oldApartment.setAvailability(apartment.getAvailability());
        oldApartment.setMax_renters(apartment.getMax_renters());
        oldApartment.setDocument_number(apartment.getDocument_number());
        oldApartment.setNumber_of_remaining(apartment.getNumber_of_remaining());
        apartmentRepository.save(oldApartment);
    }

    public void deleteApartment(Integer id){
        Apartment apartment = apartmentRepository.findApartmentById(id);
        if (apartment==null)
            throw new ApiException("Apartment not found");

        apartmentRepository.delete(apartment);
    }

    public void approveApartment(Integer apartmentId,Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        Apartment apt = apartmentRepository.findApartmentById(apartmentId);
        if (apt==null){
            throw new ApiException("Apartment not found");
        }

        apt.setApproved(true);
        apartmentRepository.save(apt);
    }
    public void rejectApartment(Integer id, String reason,Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        Apartment apartment = apartmentRepository.findApartmentById(id);
        if (apartment==null){
            throw new ApiException("Apartment not found");
        }
if (reason==null){
    throw new ApiException(reason);
}
        apartment.setApproved(false);
        apartment.setRejectionReason(reason);
        apartmentRepository.save(apartment);
    }

    //ali
    // add apartment by owner
    public void addApartmentToSakanMate(Integer id, Apartment apartment){
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner==null)
            throw new ApiException("owner not found");
        apartment.setOwner(owner);
        apartment.setNumber_of_remaining(apartment.getMax_renters());
        apartmentRepository.save(apartment);
        try {
            owner.getApartments().add(apartment); // may trigger ConcurrentModificationException
        } catch (ConcurrentModificationException e) {
        }
        ownerRepository.save(owner);

    }
}
