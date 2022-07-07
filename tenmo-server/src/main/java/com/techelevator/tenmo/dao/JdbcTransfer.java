package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTransfer implements TransferDao{


    private final JdbcTemplate jdbcTemplate;

    public JdbcTransfer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> listTransfersByUserId(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return null;
    }

    @Override
    public void updateTransferBalance(Transfer transfer) {

    }
}
