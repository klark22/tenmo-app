package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
    public Transfer getTransferById(int transferId) {
        return null;
    }

    @Override
    public void updateTransferBalance(Transfer transfer) {

    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(results.getInt("transfer_id"));
        transfer.setUser_id(results.getInt("user_id"));
        transfer.setType_id(results.getInt("type_id"));
        transfer.setStatus_id(results.getInt("status_id"));
        transfer.setAccount_from(results.getInt("account_from"));
        transfer.setAccount_to(results.getInt("account_to"));

        return transfer;

    }
}
