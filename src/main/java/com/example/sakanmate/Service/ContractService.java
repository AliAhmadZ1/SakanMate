package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;

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
}
