package com.example.transactions.entity;


import com.example.transactions.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;
    private double amount;
    private Date transactionDate;
}
