package org.example.Views;

import org.example.Models.User;

import java.lang.reflect.Field;

public class ViewHelper {

    public static void printOutObjValues(User user) {
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println(field.getName() + ": " + field.get(user));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
