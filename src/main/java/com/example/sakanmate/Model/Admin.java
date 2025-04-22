package com.example.sakanmate.Model;

import jakarta.persistence.*;
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

    private String name;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "admin")
    private Set<Complaint> complaint;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admin")
    private Set<Post> post;

}
