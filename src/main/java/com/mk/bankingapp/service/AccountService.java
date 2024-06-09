package com.mk.bankingapp.service;

import java.util.List;

import com.mk.bankingapp.dto.AccountDto;
import com.mk.bankingapp.dto.TransactionDto;

public interface AccountService {
	   AccountDto createAccount(AccountDto accountDto);
	   AccountDto getAccountById(Long id);
	   AccountDto updateAccount(Long id, AccountDto accountDto);
	   AccountDto deposit(Long id,double amount);
	   AccountDto withdraw(Long id,double amount);
	   List<AccountDto> getAllAccounts();
	   void deleteAccount(Long id);
	   List<TransactionDto> getTransactionHistory(Long accountId);
}