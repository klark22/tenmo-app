package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserServices {

    private static final String API_BASE_URL = "http://localhost:8080";
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

    public User[] listUsers() {
        User[] users = null;

        try {
            //use exchange method to pass in additional info for authorization.
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "/user", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    public Account getAccountByUserId(AuthenticatedUser currentUser) {
        Account account = null;
        long id = currentUser.getUser().getId();

        try {
            //use exchange method to pass in additional info for authorization.
            ResponseEntity<Account> response = restTemplate.exchange(API_BASE_URL + "/user/" + id, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;

    }

}
