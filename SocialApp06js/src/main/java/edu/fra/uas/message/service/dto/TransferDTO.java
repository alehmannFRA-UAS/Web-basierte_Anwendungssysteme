package edu.fra.uas.message.service.dto;

import java.io.Serializable;

public class TransferDTO implements Serializable{

    private String to;
    
    private int amount;

    public TransferDTO() {}

    public TransferDTO(String to, int amount) {
        this.to = to;
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }
    
}