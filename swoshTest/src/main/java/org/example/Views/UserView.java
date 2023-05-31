package org.example.Views;

import org.example.Controllers.UserController;
import org.example.Models.User;
import java.util.Scanner;

public class UserView {

    public static String getUserInput() {
        return new Scanner(System.in).nextLine();
    }

    public void loginPage() {

        while (true) {
            System.out.println("\n1. Log in\n2. Create account\n3. Exit");
            System.out.print("\nChoice (number) : ");

            switch (getUserInput()) {

                case "1" -> new UserController().updateLoginStatus(loginInput());

                case "2" -> new UserController().createAccount(createNewUser());

                case "3" -> {
                    System.out.println("Shutting down.");
                    return;
                }

                default -> System.out.println("Invalid choice. Please select a valid option.\n");
            }
        }
    }

    private User loginInput() {

        System.out.print("\nEnter your social security number: ");
        Long ssn = Long.parseLong(getUserInput());

        System.out.print("\nEnter your password: ");
        String psw = getUserInput();

        return new User(ssn, psw);
    }

    private User createNewUser() {

        System.out.print("\nEnter first name: ");
        String fName = getUserInput();

        System.out.print("\nEnter last name: ");
        String lName = getUserInput();

        System.out.print("\nEnter social security number: ");
        Long ssn = Long.valueOf(getUserInput());

        System.out.print("\nChoose a password: ");
        String psw = getUserInput();

        System.out.print("\nChoose a signature (four digits) : ");
        Long signature = Long.valueOf(getUserInput());

        return new User(psw, fName, lName, ssn, signature);
    }

    public void landingPage(User user) {

        while (true) {
            ViewHelper.printOutObjValues(user);
            System.out.println("""
                                        
                    1. Transactions
                    2. Accounts
                    3. Edit user credentials
                    4. Delete user account
                    5. Log out
                    """);
            System.out.print("\nChoice (number) : ");

            switch (new Scanner(System.in).nextLine()) {

                case "1" -> System.out.println("Transactions chosen");
                case "2" -> System.out.println("Accounts chosen");
                case "3" -> user = UserView.displayEditView(user);

                case "4" -> {
                    if (terminateUser(user)) return;

                }
                case "5" -> {
                    new UserController().updateLoginStatus(user);
                    return;
                }

                default -> System.out.println("Invalid choice. Please select a valid option.\n");

            }
        }

    }
    private static User displayEditView(User user) {

        while (true) {
            System.out.println("\n1. Change password.\n2. Change signature number\n3. Change name\n4. Back");
            System.out.print("\nChoice (number) : ");

            switch (getUserInput()) {

                case "1" -> user = new UserController().changeCredentials(user, "password");

                case "2" -> user = new UserController().changeCredentials(user,  "signature_number");

                case "3" -> user = new UserController().changeCredentials(user,  "name");

                case "4" -> {
                    return user;
                }

                default -> System.out.println("Invalid choice. Please select a valid option.\n");

            }
        }

    }

    private static boolean terminateUser(User user) {
        System.out.println("Confirm that you want to delete your account by entering your SSN or press ENTER to exit this choice:");
        if (user.getSsn().toString().equals(new Scanner(System.in).nextLine())) {
            if (new UserController().deleteAccount(user)) {
                return true;
            } else {
                System.out.println("Account deletion canceled!\n");
            }
        }
        return false;
    }






}
