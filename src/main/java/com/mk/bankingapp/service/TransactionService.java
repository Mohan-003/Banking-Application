package com.mk.bankingapp.service;

import com.mk.bankingapp.entity.Transaction;
import com.mk.bankingapp.entity.TransactionType;

public interface TransactionService {
    Transaction createTransaction(Long accountId, TransactionType transactionType, double amount);

}
