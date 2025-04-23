package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DTO_In.PostDTO;
import com.example.sakanmate.Model.*;
import com.example.sakanmate.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RequestRepository requestRepository;
    private final PostRepository postRepository;
    private final AdminRepository adminRepository;
    private final ApartmentRepository apartmentRepository;

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

    public void deleteOwner(Integer id) {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null)
            throw new ApiException("Owner not found");

        ownerRepository.delete(owner);
    }


    public void approveOwner(Integer ownerId,Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner==null){
            throw new ApiException("Owner not found");
        }

        owner.setApproved(true);
        ownerRepository.save(owner);
    }

    public void rejectOwnerByAdmin(Integer id, String reason,Integer adminId) {
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner==null){
            throw new ApiException("Owner not found");
        }
        if (reason==null){
            throw new ApiException(reason);
        }
        owner.setApproved(false);
        owner.setRejectionReason(reason);
        ownerRepository.save(owner);
    }

    //ali
    // add apartment by owner
    public void addApartment(Integer id, Apartment apartment){
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner==null)
            throw new ApiException("owner not found");
        apartment.setOwner(owner);
        owner.getApartment().add(apartment);
        ownerRepository.save(owner);
        apartmentRepository.save(apartment);
    }

    //ali
    public void createPost(Integer id, Integer apartment_id){
        Owner owner = ownerRepository.findOwnerById(id);
        Apartment apartment = apartmentRepository.findApartmentById(apartment_id);
        if (owner==null)
            throw new ApiException("owner not found");
        if (apartment==null)
            throw new ApiException("apartment not found");
        Post post = new Post(null,"pending", LocalDate.now(),0,false,null,null,null,apartment,owner,null);
        owner.getPosts().add(post);
        apartment.setPost(post);
        postRepository.save(post);
        ownerRepository.save(owner);
        apartmentRepository.save(apartment);
    }

    //ali
    // disable owner depend on number of complaints and rating
    public void disableOwner(Integer admin_id, Integer owner_id){
        Admin admin = adminRepository.findAdminsById(admin_id);
        Owner owner = ownerRepository.findOwnerById(owner_id);
        if (admin==null)
            throw new ApiException("admin not found");
        if (owner==null)
            throw new ApiException("owner not found");
        if (!owner.isApproved())
            throw new ApiException("owner is already disabled");
        owner.setApproved(false);
        owner.setRejectionReason("This owner did not meet the requirements.");
        ownerRepository.save(owner);
    }
}
