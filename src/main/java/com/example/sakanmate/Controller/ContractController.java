package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/contract")
public class ContractController {
    private final ContractService contractService;

    @GetMapping("/get-all-contracts")
    public ResponseEntity<List<ContractDtoOut>> getAllContracts() {
        return ResponseEntity.status(200).body(contractService.getAllContracts());
    }

    @PostMapping("/add-contract")
    public ResponseEntity<ApiResponse> addContract(@RequestBody @Valid Contract contract) {
        contractService.addContract(contract);
        return ResponseEntity.status(200).body(new ApiResponse("Contract added successfully."));
    }

    @PutMapping("/update-contract/{contractId}")
    public ResponseEntity<ApiResponse> updateContract(@PathVariable Integer contractId, @Valid @RequestBody Contract contract) {
        contractService.updateContract(contractId, contract);
        return ResponseEntity.status(200).body(new ApiResponse("Contract updated successfully."));
    }

    @DeleteMapping("/delete-contract/{contractId}")
    public ResponseEntity<ApiResponse> deleteContract(@PathVariable Integer contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.status(200).body(new ApiResponse("Contract deleted successfully."));
    }

    @PutMapping("/accept/{id}/{renterId}")
    public ResponseEntity<String> acceptContract(
            @PathVariable Integer id,
            @PathVariable Integer renterId) {
        contractService.renterAcceptContract(id, renterId);
        return ResponseEntity.ok("Contract accepted by renter.");
    }

    @GetMapping("/is-expired/{id}")
    public ResponseEntity<Boolean> isContractExpired(@PathVariable Integer id) {
        return ResponseEntity.ok(contractService.isContractExpired(id));
    }

    @PostMapping("/{oldId}/renew")
    public ResponseEntity<Contract> requestRenewal(
            @PathVariable Integer oldId,
            @RequestParam int months) {
        Contract newContract = contractService.requestRenewal(oldId, months);
        return ResponseEntity.ok(newContract);
    }

    @PutMapping("/{id}/approve-renewal")
    public ResponseEntity<String> approveRenewedContract(@PathVariable Integer id) {
        contractService.approveRenewedContract(id);
        return ResponseEntity.ok("Renewed contract approved by admin.");
    }
}
