package org.example.Models;

import java.math.BigInteger;
import java.time.LocalDateTime;


public class Transaction {
    private int id;
    private int sender;
    private int recipient;
    private LocalDateTime date_time;
    private BigInteger amount;

    public Transaction() {
    }

    public Transaction(int id, int sender, int recipient, LocalDateTime date_time, BigInteger amount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.date_time = date_time;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getSender() {
        return sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public BigInteger getAmount() {
        return amount;
    }
}
