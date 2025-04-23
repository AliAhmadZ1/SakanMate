package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Model.Post;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.OwnerRepository;
import com.example.sakanmate.Repository.PostRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final RequestRepository requestRepository;
    private final PostRepository postRepository;

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
}
