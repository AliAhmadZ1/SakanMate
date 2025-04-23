package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AdminRepository adminRepository;

    public List<Apartment> getAll(){
        return apartmentRepository.findAll();
    }

    public void addApartment(Apartment apartment){
        apartmentRepository.save(apartment);
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
            throw new RuntimeException("Admin not found");
        }
        Apartment apt = apartmentRepository.findApartmentById(apartmentId);
        if (apt==null){
            throw new RuntimeException("Apartment not found");
        }

        apt.setApproved(true);
        apartmentRepository.save(apt);
    }
}
