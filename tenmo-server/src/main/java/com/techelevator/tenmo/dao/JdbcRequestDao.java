package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;


@Component
public class JdbcRequestDao implements RequestDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Transfer createRequest(Transfer transfer) {
        Account fromAccount = new Account();
        String sqlAcc = "SELECT account_id, user_id, balance " +
                "FROM account " +
                "WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlAcc, transfer.getAccount_from());
        while (result.next()) {
            fromAccount = mapRowSetToAccount(result);
        }
        if (transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Please request amount");
            return null;
        } else if (fromAccount.getAccountId() == transfer.getAccount_to()) {
            System.out.println("Cannot request yourself, chief :(");
            return null;
        }

        // step 1: insert a row into transfer
        String sql = "INSERT INTO transfer ( transfer_type_id, " +
                "transfer_status_id, account_from, account_to, amount) " +
                "VALUES ( ?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getType_id(), transfer.getStatus_id(),
                transfer.getAccount_from(), transfer.getAccount_to(), transfer.getAmount());

        return getTransferByTransferId(newId);
    }

    @Override
    public Transfer acceptRequest(int transferId)  {
        Transfer transfer = new Transfer();

        String sql = "Select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        while (result.next()) {
            transfer = mapRowToTransfer(result);
        }

        String sql2 = "UPDATE public.transfer " +
                "SET transfer_id=?, transfer_type_id=?, transfer_status_id=?, account_from=?, account_to=?, amount=? " +
                "WHERE transfer_id = ?; ";

        jdbcTemplate.update(sql2, transfer.getTransfer_id(), transfer.getType_id(),
                2, transfer.getAccount_from(), transfer.getAccount_to(), transfer.getAmount());

        String sql3 = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql2, transfer.getAmount(), transfer.getAccount_from());

        String sql4 = "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql4, transfer.getAmount(), transfer.getAccount_to());


        return transfer;
    }

    @Override
    public Transfer rejectRequest(int transferId) {
        Transfer transfer = new Transfer();

        String sql = "Select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        while (result.next()) {
            transfer = mapRowToTransfer(result);
        }

        String sql2 = "UPDATE public.transfer " +
                "SET transfer_id=?, transfer_type_id=?, transfer_status_id=?, account_from=?, account_to=?, amount=? " +
                "WHERE transfer_id = ?; ";

        jdbcTemplate.update(sql2, transfer.getTransfer_id(), transfer.getType_id(),
                3, transfer.getAccount_from(), transfer.getAccount_to(), transfer.getAmount());


        return transfer;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfer = new Transfer();

        String sql = "Select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "WHERE transfer_id = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);

        while (result.next()) {
            transfer = mapRowToTransfer(result);

        }
        return transfer;
    }

    private Account mapRowSetToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(results.getInt("transfer_id"));
        transfer.setType_id(results.getInt("transfer_type_id"));
        transfer.setStatus_id(results.getInt("transfer_status_id"));
        transfer.setAccount_from(results.getInt("account_from"));
        transfer.setAccount_to(results.getInt("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));

        return transfer;

    }
}
