package edu.fra.uas.service.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

//import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.fra.uas.model.Account;
import edu.fra.uas.model.PayUser;
import edu.fra.uas.model.State;

@JsonPropertyOrder({ "id", "name", "state", "account" })
public class PayUserResponseDTO { // extends RepresentationModel {

    @JsonProperty("id")
    private long myId;

    private String name;

    @Enumerated(EnumType.STRING)
    private State state;

    private Account account;

    public PayUserResponseDTO() {
        account = new Account();
    }

    public PayUserResponseDTO(PayUser payUser) {
        this.myId = payUser.getId();
        this.account = payUser.getAccount();
        this.name = payUser.getName();
        this.state = payUser.getState();
    }

    public long getMyId() {
        return myId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return " [ " +
                myId + " , " +
                name + " , " +
                account.toString() + " , " +
                state + " ]"
                ;
    }

}
