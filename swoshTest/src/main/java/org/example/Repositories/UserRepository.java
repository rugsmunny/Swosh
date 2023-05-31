package org.example.Repositories;

import org.example.DataBase.DbHelper;
import org.example.Models.User;
import org.example.Views.UserView;
import org.example.Views.ViewHelper;

import java.lang.reflect.Method;

import static org.example.Views.UserView.*;

public class UserRepository {
    private static String sql;

    private static DbHelper dbH;

    public UserRepository() {
        dbH = DbHelper.getInstance();
    }

    public void setUserLoginStatus(User user) {
        sql = "UPDATE users SET logged_in=? WHERE ssn = ? AND password = ?";
        if (dbH.executeUpdate(sql, !user.getLogged_in(), user.getSsn(), user.getPassword())) {

            if (!user.getLogged_in()) {
                System.out.println("Login succeeded");
                new UserView().landingPage(fetchAllDataFromUser(user));
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

    public void createAccount(User user) {
        sql = "INSERT INTO users (first_name, last_name, password, signature_number, ssn) VALUES (?, ?, ?, ?, ?)";
        System.out.println("User account created status -> " + dbH.executeUpdate(sql, user.getFirst_name(), user.getLast_name(), user.getPassword(), user.getSignature_number(), user.getSsn()));

    }

    public boolean deleteUserAccount(User user) {
        sql = "DELETE FROM users WHERE ID = ? ";
        if (dbH.executeUpdate(sql, user.getId())) {
            System.out.println("User account deletion status -> true");
            return true;
        } else {
            System.out.println("User account deletion status -> false");
            return false;
        }

    }


    public User fetchAllDataFromUser(User user) {
        sql = "SELECT * FROM users WHERE ssn = ? AND password = ?";

        return dbH.executeQuery(sql, User.class, user.getSsn(), user.getPassword()).get(0);

    }


    public User updateCredentials(User user, String target) {

        try {
            Method getter;
            String initiateChangeValue;
            if (target.equals("name")) {
                System.out.println("Enter signature to initiate change:");
                initiateChangeValue = getUserInput();
                getter = User.class.getMethod("getSignature_number");
            } else {
                System.out.println("Enter " + target + " to initiate change:");
                initiateChangeValue = getUserInput();
                getter = User.class.getMethod("get" + capFirstLetter(target));
            }

            if (getter.invoke(DbHelper.getInstance().executeQuery("SELECT * FROM users WHERE id = ?;", User.class, user.getId()).get(0)).toString().equals(initiateChangeValue)) {

                String firstEntryValue, secondEntryValue, updateSql;

                if (target.equals("name")) {
                    updateSql = "UPDATE users SET first_name = ? AND last_name = ? WHERE id = ?;";
                    System.out.println("Enter first name :");
                    firstEntryValue = getUserInput();
                    System.out.println("Enter last name:");
                    secondEntryValue = getUserInput();

                    Object obj = new String[]{firstEntryValue, secondEntryValue, String.valueOf(user.getId())};

                    if (DbHelper.getInstance().executeUpdate(updateSql, obj)) {
                        System.out.println(target + " has been updated!");
                        user = DbHelper.getInstance().executeQuery("SELECT * FROM users WHERE id = ?;", User.class, user.getId()).get(0);
                        ViewHelper.printOutObjValues(user);
                    }
                    } else {
                        updateSql = "UPDATE users SET " + target + " = ? WHERE id = ?;";
                        System.out.println("Enter new " + target + ":");
                        firstEntryValue = getUserInput();
                        System.out.println("Repeat new " + target + ":");
                        secondEntryValue = getUserInput();
                        if (firstEntryValue.equals(secondEntryValue)) {
                            if (DbHelper.getInstance().executeUpdate(updateSql, firstEntryValue, user.getId())) {
                                System.out.println(target + " has been updated!");
                            }

                            user = DbHelper.getInstance().executeQuery("SELECT * FROM users WHERE id = ?;", User.class, user.getId()).get(0);
                            ViewHelper.printOutObjValues(user);
                        } else {
                            System.out.println(target + " update failed!");
                        }
                    }
                } else {
                    System.out.println(target + " did not match, change could not be done.");
                }
            } catch(Exception e){
                System.out.println("Updating " + target + " not possible right now, try again later. See exception -> " + e);
            }
            return user;

        }

    public static String capFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    }

