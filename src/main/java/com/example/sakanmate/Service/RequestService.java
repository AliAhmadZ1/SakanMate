package com.example.sakanmate.Service;

import com.example.sakanmate.Model.Request;
import com.example.sakanmate.Repository.RenterRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;


    public void update(){

    }

    public List<Request> getAll(){
        return requestRepository.findAll();
    }


    public void delete(){

    }

// yyyyy

    public void add(){

    }



    // hello
}

