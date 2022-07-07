package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcTransfer;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private JdbcTransfer dao;

    public TransferController(JdbcTransfer dao) {
        this.dao = dao;
    }


    @RequestMapping(path = "/{userid}", method = RequestMethod.GET)
    public List<Transfer> transferByUserId (@PathVariable int userId) {return dao.listTransfersByUserId(userId);
    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer transferByTransferId (@PathVariable int transferId) {return dao.getTransferByTransferId(transferId);
    }

    //update
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Transfer newTransfer ( @RequestBody Transfer transfer) {
        return dao.createTransfer(transfer);
    }
}
