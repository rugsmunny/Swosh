package org.example.Views;

import org.example.Controllers.AccountController;
import org.example.Models.Account;
import org.example.Models.User;
import org.json.JSONObject;

import static org.example.Helpers.ViewHelper.*;


public class AccountView {

    static AccountController ac = new AccountController();
    JSONObject credentials = new JSONObject();

    public AccountView(User user) {
        displayAccountView(user);
    }

    private void displayAccountView(User user) {

        while (true) {
            System.out.println("\n1. Create account\n2. Deposit\n3. Withdrawal\n4. Display accounts\n5. Delete account\n6. Exit.\nChoice (number) : ");
            System.out.print("");

            switch (getUserInput()) {

                case "1" -> createAccount(user);
                case "2" -> balanceUpdate(true,user.getId());
                case "3" -> balanceUpdate(false,user.getId());
                case "4" -> ac.getAccounts(user.getId());
                case "5" -> deleteAccount(user);
                case "6" -> { return; }
                default -> System.out.println("Invalid choice. Please select a valid option.\n");
            }
        }
    }

    private void balanceUpdate(boolean deposit, int userId) {
        try{
            ac.getAccounts(userId);
            sout("\nWhich account (phone number) ? -> ");
            String accNum = getUserInput();
            sout("\nAmount? -> ");
            String amount = getUserInput();
            String typeOfAction = deposit ? "deposit" : "withdrawal";
            String strBuild = "The " + typeOfAction + " of " + amount + " (SEK) was " +
                    (ac.updateAccountBalance(deposit, accNum, amount) ? " successful." : " unsuccessful.");
            sout(strBuild);
        } catch (Exception ignored){
            warning();
        }

    }


    private void createAccount(User user) {

        if (credentials.length() == 2) {
            credentials.put("user_id", user.getId());
            ac.createAccount(
                    new Account(
                            credentials.getString("phone_number"),
                            credentials.getBigInteger("deposit"),
                            credentials.getInt("user_id")));

        }credentials.clear();
        String[] formQuestions = {
                "\nInsert phone number to which your account will be used for.\nAttention! Only one phone number per account will be accepted: ",
                "\nInitial deposit (SEK) : "};

        try{
            String phoneNum, deposit;
            while(true) {
                System.out.print(formQuestions[0]);
                phoneNum = getUserInput();
                if (phoneNumberCheck(phoneNum)) {
                    System.out.print(formQuestions[1]);
                    deposit = getUserInput();
                    if(depositCheck(deposit)){
                        credentials.put("phone_number",phoneNum);
                        credentials.put("deposit",Long.parseLong(deposit));
                        return;
                    }
                }else if(phoneNum.length() < 2){
                    return;
                }
        }
    }catch (Exception ignored){
            System.out.println("Account setup failed, please try again.");
        }
    }

    private static void deleteAccount(User user) {
        ac.getAccounts(user.getId());
        System.out.println("Which account: ");
        try {

            int accountToDelete = Integer.parseInt(getUserInput());
            System.out.println("Confirm deletion of account id -> " + accountToDelete + " by entering your SSN or press ENTER to exit:");

            if (user.getSignature_number() == Integer.parseInt(getUserInput())) {
                System.out.println("Confirmation true");
                if (ac.deleteAccount(accountToDelete)) {
                    System.out.println("Account deletion completed.\n");
                } else {
                    System.out.println("Account deletion canceled!\n");
                }
            } else { System.out.println("Account deletion canceled.\n"); }
        } catch (Exception ignored) {
            System.out.println("Operation failed, use correct input type/value!\n");

        }
    }









}
