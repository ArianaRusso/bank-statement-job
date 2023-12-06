package br.com.arianarussp.bankstatementjob.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BankStatement {
    private UUID id;
    private Customer customer;
    private List<Transaction> transactions;
    private LocalDateTime timesstamp;

    public UUID getId() {
        return id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getTimesstamp() {
        return timesstamp;
    }

    public void setTimesstamp(LocalDateTime timesstamp) {
        this.timesstamp = timesstamp;
    }
}
