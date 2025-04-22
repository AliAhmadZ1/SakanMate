package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Repository.ContractRepository;
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

            // User Data (Left-Aligned, No Styling)
            document.add(new Paragraph("ID: " + contract.getId()));
//            document.add(new Paragraph("Apartment: " + contract.getApartment()));
//            document.add(new Paragraph("Owner: " + contract.getOwner()));
            document.add(new Paragraph("Price: " + contract.getTotalPrice()));
            document.add(new Paragraph("Start date: " + contract.getStartDate()));
            document.add(new Paragraph("End date: " + contract.getEndDate()));
//            document.add(new Paragraph("Renters: " + contract.getRenters()));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
