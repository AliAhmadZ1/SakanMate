package com.example.sakanmate.Controller;

import com.example.sakanmate.DtoOut.ComplaintDTO;
import com.example.sakanmate.Model.Complaint;
import com.example.sakanmate.Service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("/get")
    public List<Complaint> getAll() {
        return complaintService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addComplaint(@RequestBody ComplaintDTO dto) {
        complaintService.addComplaint(dto);
        return ResponseEntity.ok("Complaint submitted.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateComplaint(@PathVariable Integer id, @RequestBody ComplaintDTO dto) {
        complaintService.updateComplaint(id, dto);
        return ResponseEntity.ok("Complaint updated successfully.");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComplaint(@PathVariable Integer id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok("Complaint deleted successfully.");
    }
}
