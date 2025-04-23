package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.ContractRepository;
import com.example.sakanmate.Repository.RenterRepository;
import com.example.sakanmate.Repository.RequestRepository;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final RenterRepository renterRepository;
    private final RequestRepository requestRepository;

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

    public byte[] getContractAsPdf(Integer contractId){
        Contract contract = contractRepository.findContractById(contractId);
        if(contract == null) throw new ApiException("Contract not found.");
        //***Check contract status.
        return createPlainTextPdf(contract);

    }

    //This method was taking from Bealdung and customized to the contract.
    private byte[] createPlainTextPdf(Contract contract) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Title (Plain, Centered)
            Paragraph title = new Paragraph("Contract");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE); // Blank line
            // Format the renters
            StringBuilder renters = new StringBuilder("");
            for(Renter renter : contract.getRenters()){
                renters.append("Name: " + renter.getName() + "\nEmail: " + renter.getEmail() + "\n---------\n");
            }
            // Format the pdf
            document.add(new Paragraph("---------------------------------------------------------------------"));
            document.add(new Paragraph("Contract ID: " + contract.getId()));
            document.add(new Paragraph("Apartment Title: " + contract.getApartment().getTitle()));
            document.add(new Paragraph("Owner Name: " + contract.getOwner().getName()));
            document.add(new Paragraph("Contract Total Price: " + contract.getTotalPrice()));
            document.add(new Paragraph("Contract Start Date: " + contract.getStartDate()));
            document.add(new Paragraph("Contract End Date: " + contract.getEndDate()));
            document.add(new Paragraph("Renters:\n" + renters));
            document.add(new Paragraph("---------------------------------------------------------------------"));


            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    public void acceptContract(Integer renterId, Integer contractId, Integer requestId) {
        // Check if the renter exists in the database.
        Renter renter = renterRepository.findRenterById(renterId);
        if (renter == null) throw new ApiException("Renter not found.");

        // Check if the contract exists in the database.
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) throw new ApiException("Contract not found.");

        // Check if the contract belong to the renter
        Request request = requestRepository.findRequestById(requestId);
        if (request.getRenter() != renter) throw new ApiException("The Contract does not belong to the renter.");

        // Add the renter to the contract renters * renter accepting the contract.
        contract.getRenters().add(renter);
        // Save the contract.
        contractRepository.save(contract);
    }
}
