package com.mk.bankingapp.mapper;

import com.mk.bankingapp.dto.AccountDto;
import com.mk.bankingapp.entity.Account;

public class AccountMapper {
	
	public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(
                accountDto.getId(),
                accountDto.getAccountHolderName(),
                accountDto.getBalance(),
                null
        );
        return account;
    }
    public static AccountDto mapToAccountDto( Account account){
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
        return accountDto;

    }
}

