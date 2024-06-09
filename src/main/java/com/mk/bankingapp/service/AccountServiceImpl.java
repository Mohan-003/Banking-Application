package com.mk.bankingapp.service;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.bankingapp.dto.AccountDto;
import com.mk.bankingapp.dto.TransactionDto;
import com.mk.bankingapp.entity.Account;
import com.mk.bankingapp.entity.Transaction;
import com.mk.bankingapp.entity.TransactionType;
import com.mk.bankingapp.exception.AccountNotFoundException;
import com.mk.bankingapp.mapper.AccountMapper;
import com.mk.bankingapp.mapper.TransactionMapper;
import com.mk.bankingapp.repository.AccountRepository;

import jakarta.transaction.Transactional;


@Service
public class AccountServiceImpl implements AccountService {


private  AccountRepository accountRepository;
private TransactionService transactionService;

@Autowired
public AccountServiceImpl(AccountRepository accountRepository,TransactionService transactionService) {
    this.accountRepository = accountRepository;
    this.transactionService=transactionService;
}
@Override
@Transactional
public AccountDto createAccount(AccountDto accountDto) {
    Account account = AccountMapper.mapToAccount(accountDto);
    Account savedAccount = accountRepository.save(account);
    transactionService.createTransaction(savedAccount.getId(),TransactionType.DEPOSIT,savedAccount.getBalance());
    return AccountMapper.mapToAccountDto(savedAccount);
}

@Override
public AccountDto getAccountById(Long id) {
    Account account = accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException("Account doesn't exist id :"+id));
    return AccountMapper.mapToAccountDto(account);
}

@Override
@Transactional
public AccountDto deposit(Long id, double amount) {
    Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account doesn't exist id :"+id));
    double currentAmount = account.getBalance();
    double newBalance = currentAmount + amount;
    account.setBalance(newBalance);
    Account savedAccount = accountRepository.save(account);
    transactionService.createTransaction(savedAccount.getId(),TransactionType.DEPOSIT,amount);
    return AccountMapper.mapToAccountDto(savedAccount);
}

@Override
@Transactional
public AccountDto withdraw(Long id, double amount) {
    Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account doesn't exist id :"+id));
    double currentBalance = account.getBalance();
    if (currentBalance >= amount) {
        double newBalance = currentBalance - amount;
        account.setBalance(newBalance);
        Account savedAccount = accountRepository.save(account);
        transactionService.createTransaction(savedAccount.getId(),TransactionType.WITHDRAWAL,amount);
        return AccountMapper.mapToAccountDto(account);
    } else {
        throw new RuntimeException("Your account doesn't have sufficient balance");
    }
}
@Override
@Transactional
public AccountDto updateAccount(Long id, AccountDto accountDto) {
	Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account Not found with id: "+id));
		if(accountDto.getAccountHolderName() != null) {
			account.setAccountHolderName(accountDto.getAccountHolderName());
		}
		if(accountDto.getBalance() != 0) {
			account.setBalance(accountDto.getBalance());
		}
		
	Account updatedAccount = accountRepository.save(account);
    return AccountMapper.mapToAccountDto(updatedAccount);
}

@Override
public List<AccountDto> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream()
            .map((account)->AccountMapper.mapToAccountDto(account))
            .collect(Collectors.toList());
}

@Override
public void deleteAccount(Long id) {
    Account account = accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException("Account doesn't exist id :"+id));
    accountRepository.deleteById(id);
}


@Override
public List<TransactionDto> getTransactionHistory(Long id) {
    // Retrieve the account from the database
    Account account = accountRepository.findById(id)
            .orElseThrow(() ->new AccountNotFoundException("Account doesn't exist id :"+id));
    // Get the list of transactions associated with the account
    List<Transaction> transactions = account.getTransactions();
    return TransactionMapper.mapToTransactionDtoList(transactions);
    }
}