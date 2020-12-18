package com.vezenkov.restdemo.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private Date timestamp = new Date();

    @NonNull
    private String error;

    @NonNull
    private String message;
}
