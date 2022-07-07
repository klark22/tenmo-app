package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        Account account = new Account();

        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        //return this.jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, BigDecimal.class, userId);

        while (result.next()) {
            account = mapRowSetToAccount(result);
        }

        return account.getBalance();

    }





    @Override
    public void updateBalance(Account account, int id) {
        String sql = "UPDATE account " +
                "SET account_id = ?, user_id = ?, balance = ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql, account.getAccountId(), account.getUserId(), account.getBalance(), account.getAccountId());
    }

    @Override
    public List<Account> listOfAccounts() {
        List<Account> accountList = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM account; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            accountList.add(mapRowSetToAccount(result));
        }
        return accountList;

    }


    private Account mapRowSetToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }


}
