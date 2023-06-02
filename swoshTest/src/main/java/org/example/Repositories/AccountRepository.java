package org.example.Repositories;

import org.example.DataBase.QueryBuilder;
import org.example.Helpers.DbHelper;
import org.example.Models.Account;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class AccountRepository {

    private static DbHelper dbH;
    QueryBuilder qb = new QueryBuilder();

    public AccountRepository() {
        dbH = DbHelper.getInstance();
    }

    public void createAccount(Account acc) {

        qb.insertInto("accounts", "phone_number", "user_id", "balance");
        Object[] params = {acc.getPhone_number(), acc.getUser_id(), acc.getBalance()};
        System.out.println("User account created status -> " + dbH.executeUpdate(qb.build(), params));

    }

    public List<Account> getAccounts(int userId) {
        //SELECT * FROM accounts
        //WHERE user_id = (SELECT id FROM users WHERE id = user.getId());
        qb.select("accounts").where(true, "user_id = ").subQuery(new QueryBuilder().select("users", "id").where(false, "id").build());
        return dbH.executeQuery(qb.build(), Account.class, userId);
    }

    public List<Account> getAccount(int accountId) {
        //SELECT * FROM accounts
        //WHERE id = ;
        qb.select("accounts").where(false, "id");
        String sql = qb.build();
        System.out.println("min getAccount QUERY -> " + sql);
        return dbH.executeQuery(sql, Account.class, accountId);
    }

    /* public void updateAccount(Account acc, String paramToChange) {
         String entryValue, updateSql, column;

         column = paramToChange.concat(" = ? ");

         updateSql = "UPDATE accounts SET " + column + " WHERE phone_number = ?;";

         try {

             System.out.println("Enter signature to initiate change:");

             if (true*//*query på user_id för att jämföra dennes signatur med det som input .equals(getUserInput())*//*) {

                if (paramToChange.equals("phone_number")) {
                    System.out.println("Enter new phone number: ");
                } else {
                    System.out.println("Enter amount to deposit: ");
                }

                entryValue = getUserInput();

                boolean result = DbHelper.getInstance().executeUpdate(updateSql, entryValue, acc.getId());

                System.out.println(paramToChange + " update operation result -> " + result);

            } else {
                System.out.println("Updating " + paramToChange + " initiation failed, value missmatch. Please try again.");
            }

        } catch (Exception e) {
            System.out.println("Updating " + paramToChange + " not possible right now, try again later. See exception -> " + e);
        }

    }
*/
    public boolean deleteAccount(int id) {

        qb.delete("accounts").where(false, "id");
        if (dbH.executeUpdate(qb.build(), id)) {
            System.out.println("Account deletion status -> true");
            return true;
        } else {
            System.out.println("Account deletion status -> false");
            return false;
        }
    }

    public boolean checkIsBalanceEnough(String phoneNum, int amount) {

        String actionCase = " CASE WHEN balance >= " + amount + " THEN TRUE ELSE FALSE END AS sufficient_balance FROM accounts WHERE phone_number = " + phoneNum;
        qb.select("accounts", actionCase).where(false, "phone_number");
        try (Connection connection = dbH.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(qb.build())) {
            prepareStatement.setInt(1, amount);
            prepareStatement.setString(2, phoneNum);
            ResultSet rs = prepareStatement.executeQuery();

            if (rs.getFetchSize() > 0) {
                return rs.getBoolean("sufficient_balance");
            }

        } catch (SQLException ignored) {
        }
        return true;

    }


    public boolean updateAccountBalance(boolean bool, String phoneNum, String amount) {
        String actionCase =
                " CASE WHEN balance >= ? THEN balance - ? ELSE balance END ";
        if(bool){
            Object[] params = {phoneNum};
            qb.select("accounts", "balance").where(false, "phone_number");
            List<BigInteger> balance = dbH.executeQuery(qb.build(), BigInteger.class,params);
            qb.update("accounts", "balance").where(false, "phone_number");
            return dbH.executeUpdate(qb.build(), balance.get(0) + amount, phoneNum);
        } else {
            qb.update("accounts", "balance").where(false, "phone_number");
            return dbH.executeUpdate(qb.build(), actionCase, phoneNum);
        }


    }
}

