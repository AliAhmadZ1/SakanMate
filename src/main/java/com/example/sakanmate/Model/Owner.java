package com.example.sakanmate.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String password;

    private boolean isApproved = false;

    private String rejectionReason;

    private String licenseNumber;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "owner")
    private Set<Apartment> apartment;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "owner")
    private Set<Post> post;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "owner")
    private Set<Contract> contract;


}
