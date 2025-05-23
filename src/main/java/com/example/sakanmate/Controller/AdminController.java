package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.ok("Admin registered.");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getAllAdmin(@PathVariable Integer id) {
        return ResponseEntity.ok(adminService.getAllAdmin(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        adminService.updateAdmin(id, admin);
        return ResponseEntity.ok("Admin updated.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted.");
    }
}
