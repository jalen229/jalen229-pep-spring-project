package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService 
{
    @Autowired
	AccountRepository repo;

    public Account findAccountbyusername(String username) {
		
		Account account = repo.findAccountbyusername(username);
        return account;
	}

    public Account saveaccount(Account accounttocreate) {
		
		repo.saveaccount(accounttocreate.getUsername(),accounttocreate.getPassword());
        Account account = this.findAccountbyusername(accounttocreate.getUsername());
        return account;
	}
    public Account login(String username, String password){
        Account login = repo.findAccountbylogin(username, password);
        return login;
    }
    
    public Account FindAccountByAccountId( int account_id)
    {
        Account account = repo.FindAccountByAccountId(account_id);
        return account;
    }
    
}
