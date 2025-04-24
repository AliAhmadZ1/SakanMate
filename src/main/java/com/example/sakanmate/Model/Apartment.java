package com.example.sakanmate.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "The title can not be null.")
    @Column(columnDefinition = "varchar(10) not null")
    private String title;
    @NotEmpty(message = "The description can not be null.")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;
//    @NotEmpty(message = "The number_of_remaining can not be null.")
    @Column(columnDefinition = "int not null")
    private Integer number_of_remaining;
    @NotNull(message = "The max renters can not be null.")
    @Column(columnDefinition = "int not null")
    private Integer max_renters;
    @Column(columnDefinition = "boolean not null")
    private Boolean availability = false;
    @Column(columnDefinition = "varchar(40) not null")
    private String document_number;
    @Positive
    @NotNull(message = "The monthly price can not be null.")
    @Column(columnDefinition = "double not null")
    private double monthlyPrice;

    private boolean isApproved = false;

    @Column(columnDefinition = "varchar(200)")
    private String rejectionReason;


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
