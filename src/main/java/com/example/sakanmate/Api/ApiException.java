package com.example.sakanmate.Api;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
