package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.*;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ComplaintRepository;
import com.example.sakanmate.Repository.ContractRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final RequestRepository requestRepository;
    private final ContractRepository contractRepository;
    private final ComplaintRepository complaintRepository;

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

}
