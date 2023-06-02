package org.example.Repositories;

import org.example.DataBase.QueryBuilder;
import org.example.Helpers.DbHelper;
import org.example.Models.User;
import org.example.Views.UserView;

import java.lang.reflect.Method;

import static org.example.Helpers.RepoHelper.capFirstLetter;
import static org.example.Helpers.ViewHelper.getUserInput;


public class UserRepository {

    private static DbHelper dbH;
    QueryBuilder qb = new QueryBuilder();

    public UserRepository() {
        dbH = DbHelper.getInstance();
    }


    public void setUserLoginStatus(User user) {
        qb.update("users", "logged_in").where(false,"ssn", "password");
        Object[] params = {!user.getLogged_in(), user.getSsn(), user.getPassword()};
        // !user.getLogged_in(), user.getSsn(), user.getPassword() båda sätten funkar nu


        if (dbH.executeUpdate(qb.build(), params)) {

            if (!user.getLogged_in()) {
                System.out.println("Login succeeded");
                new UserView(fetchAllDataFromUser(user));
            } else {
                System.out.println("Logout succeeded");
            }

        } else {
            if (!user.getLogged_in()) {
                System.out.println("Login failed");
            } else {
                System.out.println("Logout failed");
            }
        }

    }

    public void createUserAccount(User newUser) {
        //sql = "INSERT INTO users (first_name, last_name, password, signature_number, ssn) VALUES (?, ?, ?, ?, ?)";
        qb.insertInto("users", "first_name", "last_name", "password", "signature_number", "ssn");
        Object[] params = {newUser.getFirst_name(), newUser.getLast_name(), newUser.getPassword(), newUser.getSignature_number(), newUser.getSsn()};
        System.out.println("User account created status -> " + dbH.executeUpdate(qb.build(), params));

    }

    public boolean deleteUser(User user) {
        //sql = "DELETE FROM users WHERE ID = ? ";
        qb.delete("users").where(false,"id");
        if (dbH.executeUpdate(qb.build(), user.getId())) {
            System.out.println("User account deletion status -> true");
            return true;
        } else {
            System.out.println("User account deletion status -> false");
            return false;
        }

    }

    public User fetchAllDataFromUser(User user) { //GENERISK HÄMTAR ALL DATA FÖR VAD SOM HELST
        //sql = "SELECT * FROM users WHERE ssn = ?";
        qb.select("users").where(false,"ssn");
        return dbH.executeQuery(qb.build(), User.class, user.getSsn()).get(0);

    }

    //Class<T> type// GENERISK UPPDATERAR FÖR ALLA TYPER
    public User updateCredentials(User user, String paramToChange) {

        String firstEntryValue, secondEntryValue, updateSql, column;

        column = paramToChange.equals("name") ? "first_name = ?, last_name = ? " : paramToChange.concat(" = ? ");

        updateSql = "UPDATE users SET " + column + " WHERE id = ?;";

        try {
            Method getter;
            String getterStr = paramToChange;
            if (paramToChange.equals("name")) {
                System.out.println("Enter signature to initiate change:");

                getter = User.class.getMethod("getSignature_number");
            } else {
                System.out.println("Enter " + paramToChange + " to initiate change:");

                getter = User.class.getMethod("get" + capFirstLetter(getterStr));
            }

            if (getter.invoke(fetchAllDataFromUser(user)).toString().equals(getUserInput())) {

                if (paramToChange.equals("name")) {
                    System.out.println("Enter first name :");
                } else {
                    System.out.println("Enter new " + paramToChange + ":");
                }
                firstEntryValue = getUserInput();

                if (paramToChange.equals("name")) {
                    System.out.println("Enter last name :");
                } else {
                    System.out.println("Repeat new " + paramToChange + ":");
                }
                secondEntryValue = getUserInput();


                boolean result = paramToChange.equals("name") ? DbHelper.getInstance().executeUpdate(updateSql, firstEntryValue, secondEntryValue, user.getId())
                        : DbHelper.getInstance().executeUpdate(updateSql, firstEntryValue, user.getId());

                user = result ? fetchAllDataFromUser(user) : user;
                System.out.println(paramToChange + " update operation result -> " + result);

            } else {
                System.out.println("Updating " + paramToChange + " initiation failed, value missmatch. Please try again.");
            }

        } catch (Exception e) {
            System.out.println("Updating " + paramToChange + " not possible right now, try again later. See exception -> " + e);
        }
        return user;
    }

}

