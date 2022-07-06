package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    public BigDecimal getBalance(int userId);
    public void updateBalance(Account account, int id);

}
