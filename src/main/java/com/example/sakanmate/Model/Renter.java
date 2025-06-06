package com.example.sakanmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
public class Renter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "The name can not be empty.")
    @Column(columnDefinition = "varchar(10) not null")
    private String name;
    @Email
    @NotEmpty(message = "The email can not be empty.")
    @Column(columnDefinition = "varchar(30) not null")
    private String email;
    @NotEmpty(message = "The password can not be empty.")
    private String password;
    @Pattern(regexp = "^(?i)(male|female)$", message = "Gender must be 'male' or 'female'")
    private String gender;


    @ManyToOne
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "renter")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "renter")
    private Set<Complaint> complaint;

}
