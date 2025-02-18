package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcTransfer;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")

public class TransferController {

    private JdbcTransfer dao;

    public TransferController(JdbcTransfer dao) {
        this.dao = dao;
    }


    @RequestMapping(path = "/{accountId}", method = RequestMethod.GET)
    public List<Transfer> transfersByUserId (@PathVariable int accountId) {return dao.listTransfersByUserId(accountId);
    }

    @RequestMapping(path = "/{userId}/{transferId}", method = RequestMethod.GET)
    public Transfer transferByTransferId (@PathVariable int userId, @PathVariable int transferId) {return dao.getTransferByTransferId(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Transfer createdTransfer ( @RequestBody Transfer transfer) {
        return dao.createTransfer(transfer);
    }
}
