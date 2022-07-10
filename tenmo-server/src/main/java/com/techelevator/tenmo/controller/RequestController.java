package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcRequestDao;
import com.techelevator.tenmo.model.Transfer;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/request")
@PreAuthorize("isAuthenticated()")
public class RequestController {

    private JdbcRequestDao dao;

    public RequestController(JdbcRequestDao dao) {this.dao= dao;}

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping( method = RequestMethod.POST)
    public Transfer createRequest (@RequestBody Transfer transfer) {
        return dao.createRequest(transfer);
    }

    @RequestMapping(path = "/{userId}/{transferId}", method = RequestMethod.GET)
    public Transfer transferRequestByTransferId (@PathVariable int userId, @PathVariable int transferId) {return dao.getTransferByTransferId(transferId);
    }




}
