package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private static final String API_BASE_URL = "http://hosthost:8080/account/";
    private final RestTemplate restTemplate = new RestTemplate();

    /*
    * List hotels
    * */
    public Account[] listAccounts() {
        Account[] accounts = null;

        try {
            accounts = restTemplate.getForObject(API_BASE_URL, Account[].class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return null;
    }

}
