package com.example.sakanmate.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name can not be null.")
    @Column(columnDefinition = "varchar(10) not null")
    private String name;
    @NotEmpty(message = "The email can not be null.")
    @Email
    @Column(columnDefinition = "varchar(30) not null")
    private String email;
    @NotEmpty(message = "The password can not be null.")
    @Column(columnDefinition = "varchar(25) not null")
    private String password;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "admin")
    private Set<Complaint> complaint;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admin")
    private Set<Post> post;

}
