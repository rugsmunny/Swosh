package org.example.Views;

import org.example.Controllers.UserController;
import org.example.Helpers.ViewHelper;
import org.example.Models.User;
import org.json.JSONObject;

import java.math.BigInteger;

import static org.example.Helpers.ViewHelper.*;


public class UserView {

    static UserController userController = new UserController();
    JSONObject credentials = new JSONObject();

    public UserView() {
        loginPage();
    }

    public UserView(User user) {
        landingPage(user);
    }

    private void loginPage() {

        while (true) {
            System.out.println("\n1. Log in\n2. Create account\n3. Exit");
            System.out.print("\nChoice (number) : ");

            switch (getUserInput()) {

                case "1" -> {
                    loginInput();
                    if (credentials.length() == 2) {
                        userController
                                .updateLoginStatus(
                                new User(credentials.getString("psw"), credentials.getBigInteger("ssn")));

                    }credentials.clear();
                }

                case "2" -> {
                    createNewUser();
                    if (credentials.length() == 5) {

                        userController.createAccount(
                                new User(credentials.getString("firstName"),
                                        credentials.getString("lastName"),
                                        credentials.getBigInteger("ssn"),
                                        credentials.getString("psw"),
                                        credentials.getLong("signature")));

                    } credentials.clear();

                }

                case "3" -> {
                    System.out.println("Shutting down.");
                    return;
                }

                default -> System.out.println("Invalid choice. Please select a valid option.\n");
            }
        }
    }

    private void createNewUser() {


        String[] formQuestions = {
                "\nEnter your social security number ( format YYYYMMDD-XXXX ) : ",
                "\nChoose a signature for signing transactions (four digits) : ",
                "\nEnter first name: ",
                "\nEnter last name: ",
                "\nChoose a password: "};

        String[] keys = {"ssn", "signature", "firstName", "lastName", "psw"};

        for (int i = 0; i < formQuestions.length; i++) {
            System.out.println(formQuestions[i]);
            if (!checkForValidInput(credentials, i, keys[i])) {
                break;
            }

        }
    }

    private void loginInput() {
        String input;
        while(true) {
            System.out.print("\nEnter your social security number format yyyymmdd-xxxx: ");
            input = getUserInput();
            if (isValidSSN(input)) {
                credentials.put("ssn", Long.parseLong(input.replace("-","")));
                break;
            }
            if(input.length() < 2){
                return;
            }

        }
        System.out.print("\nEnter your password: ");
        String psw = getUserInput();
        if (psw.length() < 2) {
            credentials.clear();
        } else credentials.put("psw", psw);

    }

    private void landingPage(User user) {

        while (true) {

            System.out.println("""
                                        
                    1. Transactions
                    2. Accounts
                    3. Edit user credentials
                    4. Delete user account
                    5. Log out
                    """);
            System.out.print("\nChoice (number) : ");

            switch (getUserInput()) {

                case "1" -> new TransactionView(user);
                case "2" -> new AccountView(user);
                case "3" -> user = displayEditView(user);

                case "4" -> {
                    if (terminateUser(user)) return;

                }
                case "5" -> {
                    userController.updateLoginStatus(user);
                    return;
                }

                default -> System.out.println("Invalid choice. Please select a valid option.\n");

            }
        }

    }



    private static User displayEditView(User user) {

        String[] target = {"password", "signature_number", "name"};

        while (true) {
            System.out.println("""
                    \n1. Display user info
                    \n2. Change password.
                    \n3. Change signature number
                    \n4. Change name
                    \n5. Back
                    \nChoice (number) :
                    """);

            try {
                int choice = Integer.parseInt(getUserInput());
                switch (choice) {

                    case 1 -> ViewHelper.printOutModelValues(user);

                    case 2, 3, 4 -> user = userController.changeCredentials(user, target[choice - 2]);

                    case 5 -> {
                        return user;
                    }

                    default -> System.out.println("Invalid choice. Please select a valid option.\n");

                }
            } catch (Exception ignored) {
                System.out.println("Something went wrong, please do try again.");
            }

        }

    }

    private static boolean terminateUser(User user) {
        System.out.println("Confirm account deletion by entering your SSN or press ENTER to exit:");
        try {
            if (user.getSsn().equals(BigInteger.valueOf(Long.parseLong(getUserInput())))) {
                if (userController.deleteAccount(user)) {
                    return true;
                } else {
                    System.out.println("Account deletion canceled!\n");
                }
            }
        } catch (Exception ignored) {
            System.out.println("Operation failed, use correct input type/value!\n");

        }
        return false;
    }









}
