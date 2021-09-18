package edu.fra.uas.service;

import edu.fra.uas.model.PayUser;
import edu.fra.uas.model.State;

public interface PayUserService {
	
    int getAccountBalanceByName(String userId);

    boolean containsAndAvailable(String userId);

    State getState(String userId);

    void openAccount(String userId);

    String transfer(String from, String to, int amount) throws PayUserException;

    boolean deleteUser(String userId);

    void changeUserToSuspendedState(String userId);

    PayUser getPayUser(String userId);
    
}
