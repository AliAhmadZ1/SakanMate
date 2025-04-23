package com.example.sakanmate.Repository;

import com.example.sakanmate.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Contract findContractById(Integer contractId);

    List<Contract> findByApartmentId(Integer apartmentId);
}
