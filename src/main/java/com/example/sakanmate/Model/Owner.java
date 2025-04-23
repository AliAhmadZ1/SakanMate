package com.example.sakanmate.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(columnDefinition = "varchar(30)")
    private String name;
    @NotEmpty
    @Column(columnDefinition = "varchar(30)")
    private String email;
    @NotEmpty
    @Column(columnDefinition = "varchar(30)")
    private String password;
    @Column(columnDefinition = "bool")
    private boolean isApproved = false;
    @Column(columnDefinition = "varchar(30)")
    private String rejectionReason;
    @Column(columnDefinition = "varchar(30)")
    private String licenseNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Apartment> apartments;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Contract> contracts;


}
