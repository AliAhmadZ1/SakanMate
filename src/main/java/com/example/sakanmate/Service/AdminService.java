package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ContractRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final RequestRepository requestRepository;
    private final ContractRepository contractRepository;

    public Admin getAllAdmin(Integer id) {
        return adminRepository.findAdminsById(id);
    }

    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id, Admin updatedAdmin) {
        Admin admin = adminRepository.findAdminsById(id);
        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }

        admin.setName(updatedAdmin.getName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setPassword(updatedAdmin.getPassword());


        adminRepository.save(admin);
    }

    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }

    // Admins only can make the contracts, The contract are based on the request,
    // when a request get approved by an owner the admin can create the contract.
    // Maybe we can add an endpoint for the owner where he can send a notification to an admin to create the contract
    // * Assign the task of creating the contract to an admin
    public void createContract(Integer adminId, Integer requestId) {
        // Get the admin and check if it's in the database.
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) throw new ApiException("Admin not found.");

        // Get the request and validate it.
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("Request not found.");
        switch (request.getState()) {
            case "pending" -> throw new ApiException("Can not create a contract to a pending request");
            case "rejected" -> throw new ApiException("Can not create a contract to a rejected request.");
            case "canceled" -> throw new ApiException("Can not create a contract to a canceled request");
        }
        // Get the post
        Post post = request.getPost();

        // Calculate the total price.
        double totalPrice = request.getPost().getApartment().getMonthlyPrice() * request.getMonths();

        // Create the contract.
        // The renters will be initially null, when a renter approve the contract than the renter will be added to the set of renters.
        Contract contract = new Contract(null, totalPrice, LocalDateTime.now(),
                LocalDateTime.now().plusMonths(request.getMonths()), null, request.getPost().getApartment(), request.getPost().getOwner());

        // Save the contact in the database.
        contractRepository.save(contract);
    }
}
