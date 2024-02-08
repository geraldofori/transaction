package com.example.transactions.repository;

import com.example.transactions.entity.Transaction;
import com.example.transactions.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    List<Transaction> findAll();
    List<Transaction> findBySender(User sender);

    List<Transaction> findByReceiver(User receiver);



}
