package com.example.transactions;


import com.example.transactions.entity.Transaction;
import com.example.transactions.exceptions.InvalidTransactionException;
import com.example.transactions.exceptions.TransactionNotFoundException;
import com.example.transactions.repository.TransactionRepository;
import com.example.transactions.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions();

        verify(transactionRepository, times(1)).findAll();
        assertEquals(transactions, result);
    }

    @Test
    void testGetTransactionById() {
        long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        Transaction result = transactionService.getTransactionById(transactionId);

        verify(transactionRepository, times(1)).findById(transactionId);
        assertEquals(transaction, result);
    }

    @Test
    void testGetTransactionByIdNotFound() {
        long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransactionById(transactionId));

        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        verify(transactionRepository, times(1)).save(transaction);
        assertEquals(transaction, result);
    }

    @Test
    void testCreateTransactionInvalidAmount() {
        Transaction transaction = new Transaction();
        transaction.setAmount(-10.0);

        assertThrows(InvalidTransactionException.class, () -> transactionService.createTransaction(transaction));

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void testUpdateTransaction() {
        long transactionId = 1L;
        Transaction existingTransaction = new Transaction();
        when(transactionRepository.existsById(transactionId)).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(existingTransaction);

        Transaction updatedTransaction = new Transaction();
        Transaction result = transactionService.updateTransaction(transactionId, updatedTransaction);

        verify(transactionRepository, times(1)).existsById(transactionId);
        verify(transactionRepository, times(1)).save(updatedTransaction);
        assertEquals(existingTransaction, result);
    }

    @Test
    void testUpdateTransactionNotFound() {
        long transactionId = 1L;
        when(transactionRepository.existsById(transactionId)).thenReturn(false);

        assertThrows(TransactionNotFoundException.class, () -> transactionService.updateTransaction(transactionId, new Transaction()));

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void testUpdateTransactionInvalidAmount() {
        long transactionId = 1L;
        Transaction existingTransaction = new Transaction();
        when(transactionRepository.existsById(transactionId)).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(existingTransaction);

        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setAmount(-10.0);

        assertThrows(InvalidTransactionException.class, () -> transactionService.updateTransaction(transactionId, updatedTransaction));

        verifyNoInteractions(transactionRepository);
    }

    @Test
    void testDeleteTransaction() {
        long transactionId = 1L;

        transactionService.deleteTransaction(transactionId);

        verify(transactionRepository, times(1)).deleteById(transactionId);
    }
}

