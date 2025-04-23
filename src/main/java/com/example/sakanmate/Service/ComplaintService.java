package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ComplaintDTO;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Complaint;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ApartmentRepository;
import com.example.sakanmate.Repository.ComplaintRepository;
import com.example.sakanmate.Repository.RenterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final RenterRepository renterRepository;
    private final ApartmentRepository apartmentRepository;
    private final AdminRepository adminRepository;
    public List<Complaint> getAll() {
        return complaintRepository.findAll();
    }

    public void updateComplaint(Integer id, ComplaintDTO dto) {
        Complaint complaint = complaintRepository.findComplaintById(id);
        if (complaint==null){
            throw new RuntimeException("Complaint not found");
        }

        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaintRepository.save(complaint);
    }

    public void deleteComplaint(Integer id) {
        complaintRepository.deleteById(id);
    }

    public void fileAComplaint(Integer renterId, Integer apartmentId, String title, String description) {
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the contract exists in the database.
        Apartment apartment = apartmentRepository.findApartmentById(apartmentId);
        if (apartment == null) throw new ApiException("Apartment not found.");

        // Check if the apartment does not belong to the renter
        if (!apartment.getContract().getRenters().contains(renter))
            throw new ApiException("The apartment does not belong to the renter.");

        // Make the complaint
        Complaint complaint = new Complaint(null, title, description, null, renter, apartment);
    }

    public void assignComplaintToAdmin(Integer adminId, Integer complaintId) {
        // Get the admin and check if it's in the database.
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null)
            throw new ApiException("Admin not found."); // Get the admin and check if it's in the database.
        Complaint complaint = complaintRepository.findComplaintById(complaintId);
        if (complaint == null) throw new ApiException("Compliant not found.");

        // Assign the complaint to the admin
        complaint.setAdmin(admin);

        // Save the complaint
        complaintRepository.save(complaint);
    }
}
