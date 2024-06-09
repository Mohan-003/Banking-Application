package com.mk.bankingapp.dto;

import java.time.LocalDateTime;

import com.mk.bankingapp.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private String id;
    private Long accountId;
    private TransactionType transactionType;
    private double amount;
    private LocalDateTime transactionDate;
}
