package com.example.sakanmate.Service;

import com.example.sakanmate.Api.ApiException;
import com.example.sakanmate.DtoOut.ContractDtoOut;
import com.example.sakanmate.Model.Apartment;
import com.example.sakanmate.Model.Contract;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Model.Renter;
import com.example.sakanmate.Repository.ApartmentRepository;
import com.example.sakanmate.Model.*;
import com.example.sakanmate.Repository.AdminRepository;
import com.example.sakanmate.Repository.ContractRepository;
import com.example.sakanmate.Repository.RenterRepository;
import com.example.sakanmate.Repository.RequestRepository;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.example.sakanmate.Repository.RenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
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
    private final RenterRepository renterRepository;
    private final RequestRepository requestRepository;
    private final AdminRepository adminRepository;

    public List<ContractDtoOut> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        List<ContractDtoOut> contractDtoOuts = new ArrayList<>();

        for (Contract contract : contracts) {
            ContractDtoOut contractDtoOut = new ContractDtoOut(contract.getTotalPrice(), contract.getStartDate(), contract.getEndDate());
            contractDtoOuts.add(contractDtoOut);
        }

        return contractDtoOuts;
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

    public void renterAcceptContract(Integer contractId, Integer renterId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new RuntimeException("Contract not found");
        }

        if (contract.getRenter() != null) {
            throw new RuntimeException("Contract is already accepted.");
        }

        Renter renter = renterRepository.findRenterById(renterId);
        if (renter==null){
            throw new RuntimeException("Renter not found");
        }

        contract.setRenter(renter);
        contractRepository.save(contract);

        Apartment apartment = contract.getApartment();
        apartment.setNumber_of_remaining(apartment.getNumber_of_remaining() - 1);
        apartmentRepository.save(apartment);
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
        newContract.setAdminApproved(false);
        newContract.setRenter(oldContract.getRenter());

        return contractRepository.save(newContract);
    }

    public void approveRenewedContract(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new RuntimeException("Contract not found");
        }

        if (!contract.getIsRenewed()) {
            throw new RuntimeException("This is not a renewal contract.");
        }

        contract.setAdminApproved(true);
        contractRepository.save(contract);

        Apartment apt = contract.getApartment();
        apt.setNumber_of_remaining(apt.getNumber_of_remaining() - 1);
        apartmentRepository.save(apt);
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

    // Admins only can make the contracts, The contract are based on the request,
    // when a request get approved by an owner the admin can create the contract.
    // Maybe we can add an endpoint for the owner where he can send a notification to an admin to create the contract
    // * Assign the task of creating the contract to an admin
    public void createContract(Integer adminId, Integer requestId) {
        // Get the admin and check if it's in the database.
        Admin admin = adminRepository.findAdminsById(adminId);
        if (admin == null) throw new ApiException("Admin not found.");

        // Get the request and validate it.
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("Request not found.");
        switch (request.getState()) {
            case "pending" -> throw new ApiException("Can not create a contract to a pending request");
            case "rejected" -> throw new ApiException("Can not create a contract to a rejected request.");
            case "canceled" -> throw new ApiException("Can not create a contract to a canceled request");
        }
        // Get the post
        Post post = request.getPost();

        // Calculate the total price.
        double totalPrice = post.getApartment().getMonthlyPrice() * request.getMonths();

        // Create the contract.
        // The renters will be initially null, when a renter approve the contract than the renter will be added to the set of renters.
        Contract contract = new Contract(null, totalPrice, LocalDateTime.now(),
                LocalDateTime.now().plusMonths(request.getMonths()), null, request.getPost().getApartment(), request.getPost().getOwner());

        // Save the contact in the database.
        contractRepository.save(contract);
    }
}
