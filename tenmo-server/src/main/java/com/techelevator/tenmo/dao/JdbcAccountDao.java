package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int userId) {
        Account account = new Account();

        String sql = "SELECT balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

        while (result.next()) {
            account = mapRowSetToAccount(result);
        }

        return account.getBalance();

    }

    @Override
    public void updateBalance(Account account, int id) {
        String sql = "UPDATE account " +
                "SET account_id = ?, user_id = ?, balance = ? " +
                "WHERE account_id = ?";
        jdbcTemplate.update(sql, account.getAccountId(), account.getUserId(), account.getBalance(), account.getAccountId());
    }


    private Account mapRowSetToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }


}
