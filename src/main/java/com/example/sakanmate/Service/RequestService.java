package com.example.sakanmate.Service;

import com.example.sakanmate.Repository.RenterRepository;
import com.example.sakanmate.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
}

