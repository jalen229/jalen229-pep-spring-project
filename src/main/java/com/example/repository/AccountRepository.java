package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>
{
    @Query(value = "SELECT * FROM account WHERE username = :username", nativeQuery = true)
    Account findAccountbyusername(@Param("username") String username);

    @Modifying
    @Query(value = "insert INTO account (username,password) VALUES (:username,:password)", nativeQuery = true)
    @Transactional
    void saveaccount(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM account WHERE username = ? AND password = ?", nativeQuery = true)
    Account findAccountbylogin(@Param("username") String username, @Param("password") String password);
    
    @Query(value = "SELECT * FROM account WHERE account_id = :account_id", nativeQuery = true)
    Account FindAccountByAccountId (@Param("account_id") int account_id);
}
