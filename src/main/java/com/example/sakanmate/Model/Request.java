package com.example.sakanmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "accepted|rejected|pending|canceled")
    private String state;


    @ManyToOne
    @JoinColumn(name = "request_renter_id", referencedColumnName = "id")
    @JsonIgnore
    private Renter renter;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Post post;
}
