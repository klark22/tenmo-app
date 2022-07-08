package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.RestController;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private JdbcUserDao dao;

    public UserController(JdbcUserDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path="", method = RequestMethod.GET)
    public List<User> getUserList() {
        return dao.findAll();
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int id) {
        return dao.getAccountFromUserId(id);
    }

}
