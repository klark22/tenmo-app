package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    private JdbcAccountDao dao;

    public AccountController(JdbcAccountDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public BigDecimal get(@PathVariable int id) {
        return dao.getBalance(id);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public void put(@RequestBody Account account, @PathVariable int id) {
        dao.updateBalance(account, id);
    }



}
