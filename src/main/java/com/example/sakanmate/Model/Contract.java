package com.example.sakanmate.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Positive
    @NotNull(message = "The total price can not be null.")
    @Column(columnDefinition = "double not null")
    private double totalPrice;
    @NotNull(message = "The start date can not be null.")
    @FutureOrPresent
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime startDate;
    @NotNull(message = "The end date can not be null.")
    @Future
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime endDate;
    @Column(columnDefinition = "bool")
    private Boolean isRenewed = false;
    @Column(columnDefinition = "bool")
    private Boolean ownerApproved = false;
    @Column(columnDefinition = "bool")
    private Boolean renterAccepted = false;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract")
    private Set<Renter> renters;

    @ManyToOne
    @JsonIgnore
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonIgnore
    private Owner owner;
}
