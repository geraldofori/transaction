package com.example.transactions.services;

import com.example.transactions.entity.Transaction;
import com.example.transactions.exceptions.InvalidTransactionException;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.orElseThrow(()-> new TransactionNotFoundException("Transaction not found for id"+ id));
    }

    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            throw new InvalidTransactionException("Invalid transaction amount");
        }

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Transaction not found for id: " + id);
        }

        if (updatedTransaction.getAmount() <= 0) {
            throw new InvalidTransactionException("Invalid transaction amount");
        }

        updatedTransaction.setId(id);
        return transactionRepository.save(updatedTransaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
