package edu.fra.uas.service.dto;

public class TransferDTO {

    String to;
    int amount;

    public TransferDTO() {
    }

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
