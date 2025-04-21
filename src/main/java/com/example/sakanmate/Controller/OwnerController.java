package com.example.sakanmate.Controller;

import com.example.sakanmate.Api.ApiResponse;
import com.example.sakanmate.Model.Owner;
import com.example.sakanmate.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sakan-mate/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/get")
    public ResponseEntity getAllOwners(){
        return ResponseEntity.status(200).body(ownerService.getAllOwners());
    }

    @PostMapping("/add")
    public ResponseEntity addOwner(@RequestBody@Valid Owner owner){
        ownerService.addOwner(owner);
        return ResponseEntity.status(200).body(new ApiResponse("owner added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOwner(@PathVariable Integer id, @RequestBody@Valid Owner owner){
        ownerService.updateOwner(id, owner);
        return ResponseEntity.status(200).body(new ApiResponse("owner updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOwner(@PathVariable Integer id){
        ownerService.deleteOwner(id);
        return ResponseEntity.status(200).body(new ApiResponse("owner deleted"));
    }


}
