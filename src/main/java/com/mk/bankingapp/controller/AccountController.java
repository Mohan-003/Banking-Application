package com.mk.bankingapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mk.bankingapp.dto.AccountDto;
import com.mk.bankingapp.dto.TransactionDto;
import com.mk.bankingapp.service.AccountService;

@Controller
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;
    
    public AccountController (AccountService theAccountService){
        accountService = theAccountService;
    }
    //Add Account Rest API
    @PostMapping("/createAccount")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        accountDto.setId(0L);
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }
    // get single account
    //http://localhost:8080/api/accounts/1
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id){
        return new ResponseEntity<>(accountService.getAccountById(id),HttpStatus.OK);
    }
    // update Account details
    //http://localhost:8080/api/accounts
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto){
        return new ResponseEntity<>(accountService.updateAccount(id, accountDto), HttpStatus.OK ) ;
    }

    // deposit amount
    //http://localhost:8080/api/accounts/1/deposit
    // response body: json : {"amount":20000}
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit (@PathVariable Long id,
                                               @RequestBody Map<String, Double> request){
        //Map<key,value>
        //request.get("Object key")-> to get the value
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposit(id,amount);
        return new ResponseEntity<>(accountDto,HttpStatus.OK);
        // otherwise return ResponseEntity.ok(accountDto);
    }

    // withdraw amount
    //http://localhost:8080/api/accounts/1/withdraw
    // response body: json : {"amount":20000}
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw (@PathVariable Long id,
                                               @RequestBody Map<String, Double> request){
        //Map<key,value>
        //request.get("Object key")-> to get the value
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id,amount);
        return new ResponseEntity<>(accountDto,HttpStatus.OK);
        // otherwise return ResponseEntity.ok(accountDto);
    }
    // get all account details
    @GetMapping()
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        //return new ResponseEntity<>(accountService.getAllAccounts(),HttpStatus.OK);
        // otherwise you can write like:
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    // delete account by account id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Deleted Successfully account id: "+id);
    }
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactionHistory(@PathVariable Long id) {
        List<TransactionDto> transactions = accountService.getTransactionHistory(id);
        return ResponseEntity.ok(transactions);
    }

}