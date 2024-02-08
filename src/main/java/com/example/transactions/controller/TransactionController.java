package com.example.transactions.controller;

import com.example.transactions.entity.Transaction;
import com.example.transactions.exceptions.InvalidTransactionException;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController (TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public Page<Transaction> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return transactionService.getAllTransactions(page, size);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return new ResponseEntity<Transaction>(createdTransaction, HttpStatus.CREATED).getBody();
        } catch (InvalidTransactionException e) {
            return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST).getBody();
        }
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTransaction) {
        try {
            Transaction updated = transactionService.updateTransaction(id, updatedTransaction);
            return new ResponseEntity<Transaction>(updated, HttpStatus.OK).getBody();
        } catch (TransactionNotFoundException e) {
            return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND).getBody();
        } catch (InvalidTransactionException e) {
            return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST).getBody();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return new ResponseEntity<Transaction>(HttpStatus.NO_CONTENT);
        } catch (TransactionNotFoundException e) {
            return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
        }
    }
}
