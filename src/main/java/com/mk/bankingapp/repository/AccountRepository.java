package com.mk.bankingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mk.bankingapp.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
