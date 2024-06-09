package com.mk.bankingapp.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

    private int statusCode;
    private LocalDateTime timeStamp;
    private String errorMessage;
    private String description;

}
