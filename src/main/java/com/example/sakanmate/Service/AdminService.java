package com.example.sakanmate.Service;

import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private AdminRepository repo;

    public Admin getAllAdmin(Integer id) {
        return repo.findAdminsById(id);
    }

    public void addAdmin(Admin admin) {
        repo.save(admin);
    }

    public void updateAdmin(Integer id, Admin updatedAdmin) {
        Admin admin = repo.findAdminsById(id);
        if (admin==null){
            throw new RuntimeException("Admin not found");
        }

        admin.setName(updatedAdmin.getName());
        admin.setEmail(updatedAdmin.getEmail());
        admin.setPassword(updatedAdmin.getPassword());
        admin.setType(updatedAdmin.getType());

        repo.save(admin);
    }


    public void deleteAdmin(Integer id) {
        repo.deleteById(id);
    }
}
