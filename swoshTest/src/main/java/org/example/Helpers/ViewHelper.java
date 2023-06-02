package org.example.Helpers;


import org.example.Models.User;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ViewHelper {

    public static String getUserInput() {
        return new Scanner(System.in).nextLine().trim();
    }

    public static boolean checkForValidInput(JSONObject credentials, int x, String key) {

        while (true) {

            try {
                String str = getUserInput();

                if (str.length() > 1) {
                    switch (x) {
                        case 0 -> {
                            if (isValidSSN(str)) {
                                credentials.put(key, BigInteger.valueOf(Long.parseLong(str.replace("-", ""))));
                                return true;
                            }
                        }
                        case 1 -> {
                            if (signatureCheck(str)) {
                                credentials.put(key, Long.parseLong(str));
                                return true;
                            }
                        }
                        case 2, 3 -> {
                            if (containsOnlyLetters(str)) {
                                credentials.put(key, str);
                                return true;
                            }
                        }
                        case 4 -> {
                            credentials.put(key, str);
                            return true;
                        }
                    }

                } else {
                    credentials.clear();
                    return false;
                }
            } catch (Exception ignored) {
                System.out.println("Something went wrong, please try again or come back later.");
            }

            if (credentials.length() == 5) {
                return true;
            }
        }
    }

    public static boolean containsOnlyLetters(String str) {
        if (!str.matches("[a-öA-Ö]+")) {
            System.out.println("Use only letters, no numbers or special characters. ( To exit register process just press ENTER )");
            return false;
        }
        return true;
    }

    public static boolean signatureCheck(String str) {
        if (str.length() != 4 || !str.matches("[0-9]+")) {
            System.out.println("Your signature should only contain 4 numbers. ( To exit register process just press ENTER )");
            return false;
        }

        return true;
    }

    public static boolean depositCheck(String str) {
        if (Integer.parseInt(str) < 0 || !str.matches("[0-9]+")) {
            System.out.println("Deposit value does not compjutter at all, please try again. ( To exit register process just press ENTER )");
            return false;
        }

        return true;
    }

    public static boolean phoneNumberCheck(String str) {
        if (str.length() != 10 || (!str.matches("[0-9]+") || !str.startsWith("07"))) {
            System.out.println("The cellphone number should consist of exactly 10 digits. ( To exit register process just press ENTER )");
            return false;
        }

        return true;
    }

    public static boolean isValidSSN(String str) {
        if (str.matches("\\d{8}-\\d{4}") || str.matches("\\d{8}\\d{4}")) {

            String dateStr = str.substring(0, 8);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(dateStr, formatter);

            LocalDate minDate = LocalDate.now().minusYears(18);
            LocalDate maxDate = LocalDate.now().minusYears(100);

            if (date.isAfter(minDate)) {
                System.out.println("You can't be serious(...?) ( To exit register process just press ENTER )");
                return false;
            } else if (date.isBefore(maxDate)) {
                System.out.println("The minimum age to register is 18. ( To exit register process just press ENTER )");
                return false;
            }
        } else {
            System.out.println("Use only numbers and the right SSN format YYYYMMDD-XXXX. ( To exit register process just press ENTER )");
            return false;
        }
        return true;
    }

/*    public static void printOutObjValues(User user) {
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!field.getName().equals("id") && !field.getName().equals("password") && !field.getName().equals("signature_number") && !field.getName().equals("logged_in")) {
                    System.out.println(field.getName() + ": " + field.get(user));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }*/
    public static <T> void printOutModelValues(T model) {
        try {
            Field[] fields = model.getClass().getDeclaredFields();
            System.out.println("\n---------------------------------------------\n");
            for (Field field : fields) {
                field.setAccessible(true);

                if(model instanceof  User){
                    if (!field.getName().equals("id") && !field.getName().equals("password") && !field.getName().equals("signature_number") && !field.getName().equals("logged_in")) {
                        System.out.println(field.getName() + ": " + field.get(model));
                    }
                } else {

                    System.out.println(field.getName() + ": " + field.get(model));
                }

            }
            System.out.println("\n---------------------------------------------");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access fields of the object", e);
        }
    }
    public static void sout(String str){
        System.out.println(str);
    }

    public static void warning() {
        System.out.println("Your actions are being monitored, tread carefully.");
    }

}
