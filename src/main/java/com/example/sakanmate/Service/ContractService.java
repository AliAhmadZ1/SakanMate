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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ApartmentRepository apartmentRepository;
    private final RenterRepository renterRepository;
    private final RequestRepository requestRepository;
    private final AdminRepository adminRepository;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public List<ContractDtoOut> getAllContracts() {
        List<Contract> contracts = contractRepository.findAll();
        List<ContractDtoOut> contractDtoOuts = new ArrayList<>();

        for (Contract contract : contracts) {
            ContractDtoOut contractDtoOut = new ContractDtoOut(contract.getTotalPrice(), contract.getStartDate(), contract.getEndDate());
            contractDtoOuts.add(contractDtoOut);
        }

        return contractDtoOuts;
    }

    public void updateContract(Integer contractId, Contract contract) {
        Contract tempContractObject = contractRepository.findContractById(contractId);
        if (tempContractObject == null) throw new ApiException("Contract not found.");
        tempContractObject.setEndDate(contract.getEndDate());
        tempContractObject.setStartDate(contract.getStartDate());
        tempContractObject.setTotalPrice(contract.getTotalPrice());
        contractRepository.save(tempContractObject);
    }

    public void deleteContract(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) throw new ApiException("Contract not found.");
        contractRepository.delete(contract);
    }

    // Ayman
    public byte[] getContractAsPdf(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) throw new ApiException("Contract not found.");
        //***Check contract status.
        return createPlainTextPdf(contract);

    }

    // Ayman
    // This method was taking from Bealdung and customized to the contract.
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
            for (Renter renter : contract.getRenters()) {
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


    public boolean isContractExpired(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new ApiException("Contract not found");
        }

        return LocalDateTime.now().isAfter(contract.getEndDate());
    }

    public Contract requestRenewal(Integer oldContractId, int monthsToExtend) {
        Contract oldContract = contractRepository.findContractById(oldContractId);
        if (oldContract==null){
            throw new ApiException("Old contract not found");
        }

        if (LocalDateTime.now().isBefore(oldContract.getEndDate())) {
            throw new ApiException("Current contract is still active");
        }

        Contract newContract = new Contract();
        newContract.setApartment(oldContract.getApartment());
        newContract.setOwner(oldContract.getOwner());
        newContract.setStartDate(LocalDateTime.now());
        newContract.setEndDate(LocalDateTime.now().plusMonths(monthsToExtend));
        newContract.setTotalPrice(oldContract.getTotalPrice() * monthsToExtend);
        newContract.setIsRenewed(true);
        newContract.setRenters(oldContract.getRenters());
        newContract.setOwnerApproved(false);
        newContract.setRenters(oldContract.getRenters());

        return contractRepository.save(newContract);
    }

    public void approveRenewedContract(Integer contractId, Integer ownerId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract==null){
            throw new ApiException("Contract not found");
        }

        if (!contract.getIsRenewed()) {
            throw new ApiException("This is not a renewal contract.");
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

    //Ayman
    // Called by the renter to accept a contract.
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

        // Check if the renter accepted the contract
        if (contract.getRenters().contains(renter)) throw new ApiException("Renter already accepted the contract.");

        // Add the contract to the renter
        renter.setContract(contract);
        renterRepository.save(renter);
        // Add the renter to the contract renters * renter accepting the contract.
        contract.getRenters().add(renter);
        // Save the contract.
        contractRepository.save(contract);
    }

    //Ayman
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
        Contract contract = new Contract(null, totalPrice, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusMonths(request.getMonths()), false, false, false, null, request.getPost().getApartment(), request.getPost().getOwner());

        // Save the contact in the database.
        contractRepository.save(contract);
    }

    public void ownerApproveContract(Integer contractId, Integer ownerId) {
        Contract contract = contractRepository.findContractById(contractId);
        if (contract == null) {
            throw new ApiException("Contract not found.");
        }
        if (contract.getOwner() == null) {

            throw new ApiException("Owner not found");
        }
        if (!contract.getOwner().getId().equals(ownerId)) {
            throw new ApiException("Contract does not belong to the owner");
        }

        Apartment apartment = contract.getApartment();

        int currentRentersCount = contract.getRenters().size();
        int requiredRentersCount = apartment.getMax_renters();

        if (currentRentersCount < requiredRentersCount) {
            throw new ApiException("Only " + currentRentersCount + " renters accepted. Required: " + requiredRentersCount);
        }

        contract.setOwnerApproved(true);
        contractRepository.save(contract);
        sendEmailToRentersWhenContractComplete(contract.getRenters());
    }

    public void sendEmailToRentersWhenContractComplete(Set<Renter> renters) {
        for (Renter renter : renters) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(renter.getEmail());
            simpleMailMessage.setText("Hello " + renter.getName() + "\nYour contract have been approved by the owner");
            simpleMailMessage.setSubject("Contract new update");
            simpleMailMessage.setSentDate(Date.from(Instant.now()));
            javaMailSender.send(simpleMailMessage);
        }
    }


    public void contractEndingNotification(Integer contract_id,Integer admin_id, Integer renter_id){
        Contract contract = contractRepository.findContractById(contract_id);
        Admin admin = adminRepository.findAdminsById(admin_id);
        Renter renter = renterRepository.findRenterById(renter_id);
        if (contract==null)
            throw new ApiException("Contract not found");
        if (admin==null)
            throw new ApiException("Admin not found");
        if (renter==null)
            throw new ApiException("renter not found");
        if (renter.getContract().getId()!=contract_id)
            throw new ApiException("renter not related to the contract");

        if (LocalDateTime.now().plusDays(11).isBefore(contract.getEndDate()))
            throw new ApiException("send notification not now should be before 10 days from end date");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(renter.getEmail());
        simpleMailMessage.setText("The contract is about to expire. \nPlease renew the contract if you want to continue as a renter.");
        simpleMailMessage.setSubject("Contract Expire attention");
        simpleMailMessage.setSentDate(Date.from(Instant.now()));
        javaMailSender.send(simpleMailMessage);
    }

}
