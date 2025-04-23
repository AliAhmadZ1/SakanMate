package com.example.sakanmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The title can not be empty.")
    @Column(columnDefinition = "varchar(20) not null")
    private String title;
    @NotEmpty(message = "The description can not be empty.")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @ManyToOne
    @JoinColumn(name = "admin_complaint_id", referencedColumnName = "id")
    @JsonIgnore
    private Admin admin;
    @ManyToOne
    @JoinColumn(name = "renter_complaint_id", referencedColumnName = "id")
    @JsonIgnore
    private Renter renter;

    @ManyToOne
    @JoinColumn(name = "apartment_complaint_id", referencedColumnName = "id")
    @JsonIgnore
    private Apartment apartment;
}
