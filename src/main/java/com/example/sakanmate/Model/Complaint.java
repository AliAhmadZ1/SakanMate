package com.example.sakanmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private String title;
    private String description;
    private Integer apartmentId;
    private Integer renterId;

    @ManyToOne
    @JoinColumn(name = "admin_complaint_id", referencedColumnName = "id")
    @JsonIgnore
    private Admin admin;
    @ManyToOne
    @JoinColumn(name = "renter_complaint_id", referencedColumnName = "id")
    @JsonIgnore
    private Renter renter;
}
