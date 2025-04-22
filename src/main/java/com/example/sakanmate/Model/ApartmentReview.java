package com.example.sakanmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String description;


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Renter renter;

    @ManyToOne
    @JoinColumn(name = "apartment_review_id", referencedColumnName = "id")
    @JsonIgnore
    private Apartment apartment;

}
