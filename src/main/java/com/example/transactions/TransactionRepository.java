package com.example.transactions;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository  extends CrudRepository<Transaction, Integer> {
}
