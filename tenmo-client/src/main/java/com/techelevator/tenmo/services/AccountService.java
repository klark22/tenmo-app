package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private static final String API_BASE_URL = "http://hosthost:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    //    put reused lines into one method:
    //    HttpHeaders headers = new HttpHeaders();
    //    headers.setBearerAuth(authToken);
    //     HttpEntity<Void> entity = new HttpEntity<>(headers);
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    //Create makeTransfer Entity for add and update requests to return transfer content
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }


    /*
     * List hotels
     * */
    public Account[] listAccounts() {
        Account[] accounts = null;

        try {

            //use exchange method to pass in additional info for authorization.
            ResponseEntity<Account[]> response = restTemplate.exchange(API_BASE_URL + "/account/", HttpMethod.GET, makeAuthEntity(), Account[].class);

            accounts = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return accounts;
    }

    public BigDecimal getBalance(int userId) {
        BigDecimal balance = null;

        try {

            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_BASE_URL + "/account/" + userId, HttpMethod.GET, makeAuthEntity(), BigDecimal.class);

            balance = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return balance;
    }

    public Transfer[] listTransfersByUserId(int userId) {
        Transfer[] transfers = null;

        try {  //USE makeAUTH ENTITY becaause its not a put or post
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL +"/transfer/" + userId, HttpMethod.GET, makeAuthEntity(), Transfer[].class);

            transfers = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfers;
    }


    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfers = null;

        try {  //USE makeAUTH ENTITY becaause its not a put or post
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL +"/transfer/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class);

            transfers = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfers;
    }

    public Transfer createTransfer(Transfer newTransfer) {
        try {
            restTemplate.exchange(API_BASE_URL +"/transfer/", HttpMethod.POST, makeTransferEntity(newTransfer), Transfer.class );

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return newTransfer;
    }

    }

