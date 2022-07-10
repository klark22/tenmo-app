package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;



public interface RequestDao {

    public Transfer createRequest(Transfer transfer);

    // create transfer object with pending status

    public Transfer acceptRequest(int transferId);

    // update balances, retrieve transfer object with transfer id and update transfer status to accepted


    public Transfer rejectRequest(int transferId);

    //  retrieve transfer object with transfer id and update transfer status to rejected

    public Transfer getTransferByTransferId(int transferId);



}
