package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;

import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserServices;
import com.techelevator.tenmo.services.RequestService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AccountService accountService = new AccountService();
    private final UserServices userServices = new UserServices();
    private final RequestService requestService = new RequestService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
        accountService.setAuthToken(currentUser.getToken());
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void printTransfersOrError(Transfer[] transfers) {
        if (transfers != null) {
            consoleService.printTransfers(transfers);
        }else {
            consoleService.printErrorMessage();
        }
    }

    private void printUsersOrError(User[] users) {
        if (users != null) {
            consoleService.printUsers(users);
        }else {
            consoleService.printErrorMessage();
        }
    }

	private void viewCurrentBalance() {
        BigDecimal balance = accountService.getBalance(currentUser);
        System.out.print("Current balance: " + balance);

	}

	private void viewTransferHistory() {

        Transfer[] transfers = accountService.listTransfersByUserId(currentUser);
        printTransfersOrError(transfers);

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        User[] users = userServices.listUsers();
        printUsersOrError(users);

        int from_account_id = userServices.getAccountByUserId(currentUser).getAccountId();

        //Get user input for to_account
        int to_userId = consoleService.promptForInt("Please choose an option: ");
        int to_accountId = to_userId + 2000;

        //Get user input for amount
        BigDecimal amountToSendInput = consoleService.promptForBigDecimal("Please enter the amount to send: ");

        //Create a new transaction object with transaction constructor
        Transfer newTransfer = new Transfer();
        newTransfer.setType_id(1);
        newTransfer.setStatus_id(2);
        newTransfer.setAccount_from(from_account_id);
        newTransfer.setAccount_to(to_accountId);
        newTransfer.setAmount(amountToSendInput);

        //Pass new transaction into the createTransfer method
        accountService.createTransfer(newTransfer);

		
	}

	private void requestBucks() {
		// TODO Auto-generated method stub

        //list the users
        // include error functions for requesting to self, $0, negatives
        //test break vs null for error requests
        // get user input for account from
        //get user input for amount

        // create new transaction object

        //List users
        User[] users = userServices.listUsers();
        printUsersOrError(users);

        //current user info
        int requesting_user_id = userServices.getAccountByUserId(currentUser).getAccountId();

        //  Get user input for person who will receive request
        int request_money_from_user_id = consoleService.promptForInt("Please make a selection: ");
        int to_accountId = request_money_from_user_id + 2000;

        //Get user input for amount
        BigDecimal amountToSendInput = consoleService.promptForBigDecimal("Please enter the amount to request: ");

        //Create a new transaction object with transaction constructor
        Transfer requestTransfer = new Transfer();
        requestTransfer.setType_id(1);
        requestTransfer.setStatus_id(1);
        requestTransfer.setAccount_from(requesting_user_id);
        requestTransfer.setAccount_to(to_accountId);
        requestTransfer.setAmount(amountToSendInput);

        //Pass new transaction into the createTransfer method
        accountService.createRequest(requestTransfer);
		
	}

}
