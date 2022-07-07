package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransfer implements TransferDao{


    private final JdbcTemplate jdbcTemplate;

    public JdbcTransfer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> listTransfersByUserId(int userId) {
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql = "SELECT * " +
                    "From transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            transfers.add(mapRowToTransfer(results));
        }

        return transfers;
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

    @Override
    public void updateTransferBalance(int accountFromId, int accountToId, BigDecimal transferAmount) {

    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer ( transfer_type_id, " +
                "transfer_status_id, account_from, account_to, amount) " +
                "VALUES ( ?, ?, ?, ?, ?) RETURNING transfer_id;";



        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getType_id(), transfer.getStatus_id(),
                transfer.getAccount_from(),transfer.getAccount_to(), transfer.getAmount());
        return getTransferByTransferId(newId);
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(results.getInt("transfer_id"));
        transfer.setType_id(results.getInt("transfer_type_id"));
        transfer.setStatus_id(results.getInt("transfer_status_id"));
        transfer.setAccount_from(results.getInt("account_from"));
        transfer.setAccount_to(results.getInt("account_to"));

        return transfer;

    }
}
