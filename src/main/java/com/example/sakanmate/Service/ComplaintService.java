package com.example.sakanmate.Service;

import com.example.sakanmate.DtoOut.ComplaintDTO;
import com.example.sakanmate.Model.Complaint;
import com.example.sakanmate.Repository.ComplaintRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComplaintService {
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAll() {
        return complaintRepository.findAll();
    }

    public void addComplaint(ComplaintDTO dto) {
        Complaint c = new Complaint();
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        c.setApartmentId(dto.getApartmentId());
        complaintRepository.save(c);
    }


    public void updateComplaint(Integer id, ComplaintDTO dto) {
        Complaint complaint = complaintRepository.findComplaintById(id);
        if (complaint==null){
            throw new RuntimeException("Complaint not found");
        }

        complaint.setTitle(dto.getTitle());
        complaint.setDescription(dto.getDescription());
        complaint.setApartmentId(dto.getApartmentId());

        complaintRepository.save(complaint);
    }

    public void deleteComplaint(Integer id) {
        complaintRepository.deleteById(id);
    }
}
