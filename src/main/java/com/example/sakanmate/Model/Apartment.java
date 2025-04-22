package com.example.sakanmate.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private Integer max_renter;

    private Boolean availability;

    private String document_number;



    @OneToOne(cascade = CascadeType.ALL, mappedBy = "apartment")
    @PrimaryKeyJoinColumn
    private Post post;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment")
    private Set<ApartmentReview> apartmentReviews;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "apartment")
    @PrimaryKeyJoinColumn
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "apartment_id" , referencedColumnName = "id")
    @JsonIgnore
    private Owner owner;

}
