package com.example.sakanmate.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    private Integer number_of_remaining;

    private Integer max_renters;

    private Boolean availability = false;

    private String document_number;
    @Positive
    @NotNull(message = "The monthly price can not be null.")
    @Column(columnDefinition = "double not null")
    private double monthlyPrice;

    private boolean isApproved = false;


    @OneToMany(mappedBy = "apartment")
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment")
    private Set<ApartmentReview> apartmentReviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment")
    private Set<Contract> contracts;

    @ManyToOne
    @JoinColumn(name = "owner_id" , referencedColumnName = "id")
    @JsonIgnore
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "apartment")
    private Set<Complaint> complaint;

}
