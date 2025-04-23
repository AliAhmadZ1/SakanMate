package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.DtoOut.ComplaintDTO;
import com.example.sakanmate.Model.Complaint;
import com.example.sakanmate.Service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/complaint")
public class ComplaintController {

    private final ComplaintService complaintService;

    @GetMapping("/get")
    public List<Complaint> getAll() {
        return complaintService.getAll();
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

    @PostMapping("/file-complaint/{renterId}/{apartmentId}")
    public ResponseEntity<ApiResponse> fileComplaint(@PathVariable Integer renterId, @PathVariable Integer apartmentId, @RequestBody String title, @RequestBody String description){
        complaintService.fileAComplaint(renterId, apartmentId, title, description);
        return ResponseEntity.status(200).body(new ApiResponse("Complaint filed successfully."));
    }

    @PutMapping("/assign-complaint-to-admin/{adminId}/{complaintId}")
    public ResponseEntity<ApiResponse> assignComplaintToAdmin(@PathVariable Integer adminId, @PathVariable Integer complaintId){
        complaintService.assignComplaintToAdmin(adminId, complaintId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Complaint assigned successfully/"));
    }
}
