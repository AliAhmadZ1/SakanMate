package com.example.sakanmate.Controller;

import com.example.sakanmate.Model.Admin;
import com.example.sakanmate.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sakan-mate/admin")
public class AdminController {

    private final AdminService service;

    @PostMapping("/add")
    public ResponseEntity addAdmin(@RequestBody Admin admin) {
        service.addAdmin(admin);
        return ResponseEntity.ok("Admin registered.");
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity getAllAdmin(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getAllAdmin(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable Integer id, @RequestBody Admin admin) {
        service.updateAdmin(id, admin);
        return ResponseEntity.ok("Admin updated.");
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Integer id) {
        service.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted.");
    }
}
