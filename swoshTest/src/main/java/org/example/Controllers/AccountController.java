package org.example.Controllers;

import org.example.Helpers.ViewHelper;
import org.example.Models.Account;
import org.example.Repositories.AccountRepository;
import java.util.List;



public class AccountController {
AccountRepository ar = new AccountRepository();

    public void createAccount(Account acc) {

        ar.createAccount(acc);

    }
/*    public void updateAccount(Account acc, String paramToChange) {

        ar.updateAccount(acc, paramToChange);

    }*/

    public boolean deleteAccount(int id) {

       return ar.deleteAccount(id);

    }

    public void getAccounts(int id) {

        List<Account> accountList = ar.getAccounts(id);
        if(accountList != null && accountList.size() > 0){
        accountList.forEach(ViewHelper::printOutModelValues);
        }

    }


    public boolean updateAccountBalance(boolean deposit, String accNum, String amount) {
        return ar.updateAccountBalance(deposit, accNum, amount);

    }
}
