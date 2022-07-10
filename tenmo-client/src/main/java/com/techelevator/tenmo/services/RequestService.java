package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class RequestService {

    private static final String API_BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);

}

public Transfer createRequest(Transfer newRequest) {
        try {
            restTemplate.exchange(API_BASE_URL +"/request/", HttpMethod.POST,
                    makeTransferEntity(newRequest), Transfer.class );

            } catch (RestClientResponseException | ResourceAccessException e) {
                    BasicLogger.log(e.getMessage());
        }

        return newRequest;
}

    public Transfer getTransferByTransferId(int transferId) {
        Transfer transfers = null;

        try {  //USE makeAUTH ENTITY becaause its not a put or post
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL +"/request" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class);

            transfers = response.getBody();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfers;
    }
}
