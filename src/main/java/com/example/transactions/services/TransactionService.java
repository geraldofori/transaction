package com.example.transactions.services;

import com.example.transactions.entity.Transaction;
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
        return optionalTransaction.orElse(null);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        if (transactionRepository.existsById(id)) {
            updatedTransaction.setId(id);
            return transactionRepository.save(updatedTransaction);
        } else {
            return null; // Handle case when the transaction with the given ID doesn't exist
        }
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
