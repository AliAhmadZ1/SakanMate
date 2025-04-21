package com.example.sakanmate.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean state;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private Set<Renter> renters;
    //private Apartment apartment;
}
