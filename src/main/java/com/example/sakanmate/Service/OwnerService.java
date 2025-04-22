package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public List<Owner> getAllOwners(){
        return ownerRepository.findAll();
    }

    public void addOwner(Owner owner){
        ownerRepository.save(owner);
    }

    public void updateOwner(Integer id,Owner owner){
        Owner oldOwner = ownerRepository.findOwnerById(id);
        if (oldOwner==null)
            throw new ApiException("Owner not found");

        oldOwner.setEmail(owner.getEmail());
        oldOwner.setName(owner.getName());
        oldOwner.setPassword(owner.getPassword());
        ownerRepository.save(oldOwner);
    }

    public void deleteOwner(Integer id){
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner==null)
            throw new ApiException("Owner not found");

        ownerRepository.delete(owner);
    }

    public void approveOwner(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner==null){
            throw new RuntimeException("Owner not found");
        }

        owner.setApproved(true);
        ownerRepository.save(owner);
    }

    public void rejectOwnerByAdmin(Integer id, String reason) {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner==null){
            throw new RuntimeException("Owner not found");
        }

        owner.setApproved(false);
        owner.setRejectionReason(reason);
        ownerRepository.save(owner);
    }


}
