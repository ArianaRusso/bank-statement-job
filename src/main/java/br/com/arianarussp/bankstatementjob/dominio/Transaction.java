package br.com.arianarussp.bankstatementjob.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timesstamp;
    private UUID receiver_id;
    private UUID sender_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimesstamp() {
        return timesstamp;
    }

    public void setTimesstamp(LocalDateTime timesstamp) {
        this.timesstamp = timesstamp;
    }

    public UUID getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(UUID receiver_id) {
        this.receiver_id = receiver_id;
    }

    public UUID getSender_id() {
        return sender_id;
    }

    public void setSender_id(UUID sender_id) {
        this.sender_id = sender_id;
    }
}
