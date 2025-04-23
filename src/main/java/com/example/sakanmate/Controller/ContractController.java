package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/get-contract-as-pdf/{contractId}")
    public ResponseEntity<byte[]> getContractAsPdf(@PathVariable Integer contractId) {
        byte[] pdfBytes = contractService.getContractAsPdf(contractId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract-" + contractId + ".pdf") // Forces download
                .contentType(MediaType.APPLICATION_PDF) // Sets MIME type
                .body(pdfBytes);
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

    @PutMapping("/accept-contract/{renterId}/{contractId}/{requestId}")
    public ResponseEntity<ApiResponse> acceptContract(@PathVariable Integer renterId, @PathVariable Integer contractId, @PathVariable Integer requestId){
        contractService.acceptContract(renterId, contractId, requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Contract accepted successfully."));
    }

    @PostMapping("/create-contract/{adminId}/{requestId}")
    public ResponseEntity<ApiResponse> createContract(@PathVariable Integer adminId, @PathVariable Integer requestId){
        contractService.createContract(adminId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Contract created successfully/"));
    }
}
