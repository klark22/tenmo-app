package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    // list of transfers by user id

     List<Transfer> listTransfersByUserId (int userId);

    // get transfer by transfer id

    Transfer getTransferById(int transferId);

    // update balance

    void updateTransferBalance (Transfer transfer);




}
