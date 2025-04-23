package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Repository.ApartmentRepository;
import com.example.sakanmate.Repository.ContractRepository;
import com.example.sakanmate.Repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ApartmentRepository apartmentRepository;
    private final RenterRepository renterRepository;

    public List<ContractDtoOut> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        List<ContractDtoOut> contractDtoOuts = new ArrayList<>();

        for (Contract contract : contracts) {
            ContractDtoOut contractDtoOut = new ContractDtoOut(contract.getTotalPrice(), contract.getStartDate(), contract.getEndDate());
            contractDtoOuts.add(contractDtoOut);
        }

        return contractDtoOuts;
    }

    public void addContract(Contract contract){
        contractRepository.save(contract);
    }

    public void updateContract(Integer contractId, Contract contract){
        Contract tempContractObject = contractRepository.findContractById(contractId);
        if(tempContractObject == null) throw new ApiException("Contract not found.");
        tempContractObject.setEndDate(contract.getEndDate());
        tempContractObject.setStartDate(contract.getStartDate());
        tempContractObject.setTotalPrice(contract.getTotalPrice());
        contractRepository.save(tempContractObject);
    }

    public void deleteContract(Integer contractId){
        Contract contract = contractRepository.findContractById(contractId);
        if(contract == null) throw new ApiException("Contract not found.");
        contractRepository.delete(contract);
    }

    public boolean isContractExpired(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new RuntimeException("Contract not found");
        }

        return LocalDateTime.now().isAfter(contract.getEndDate());
    }

    public Contract requestRenewal(Integer oldContractId, int monthsToExtend) {
        Contract oldContract = contractRepository.findContractById(oldContractId);
        if (oldContract==null){
            throw new RuntimeException("Old contract not found");
        }

        if (LocalDateTime.now().isBefore(oldContract.getEndDate())) {
            throw new RuntimeException("Current contract is still active");
        }

        Contract newContract = new Contract();
        newContract.setApartment(oldContract.getApartment());
        newContract.setOwner(oldContract.getOwner());
        newContract.setStartDate(LocalDateTime.now());
        newContract.setEndDate(LocalDateTime.now().plusMonths(monthsToExtend));
        newContract.setTotalPrice(oldContract.getTotalPrice() * monthsToExtend);
        newContract.setIsRenewed(true);
        newContract.setOwnerApproved(false);
        newContract.setRenters(oldContract.getRenters());

        return contractRepository.save(newContract);
    }

    public void approveRenewedContract(Integer contractId,Integer ownerId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new RuntimeException("Contract not found");
        }

        if (!contract.getIsRenewed()) {
            throw new RuntimeException("This is not a renewal contract.");
        }
        if (contract.getOwner() == null || !contract.getOwner().getId().equals(ownerId)) {

            throw new ApiException("Owner not found");
        }

        contract.setOwnerApproved(true);
        contractRepository.save(contract);

        Apartment apt = contract.getApartment();
        apt.setNumber_of_remaining(apt.getNumber_of_remaining() - 1);
        apartmentRepository.save(apt);
    }

    public void ownerApproveContract(Integer contractId,Integer ownerId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new ApiException("Contract not found.");
        }
        if (contract.getOwner() == null || !contract.getOwner().getId().equals(ownerId)) {

            throw new ApiException("Owner not found");
        }

        Apartment apartment = contract.getApartment();

        int currentRentersCount = contract.getRenters().size();
        int requiredRentersCount = apartment.getMax_renter();

        if (currentRentersCount < requiredRentersCount) {
            throw new ApiException("Only " + currentRentersCount + " renters accepted. Required: " + requiredRentersCount);
        }

        contract.setOwnerApproved(true);
        contractRepository.save(contract);
    }

}
