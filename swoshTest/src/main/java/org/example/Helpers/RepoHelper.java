package org.example.Helpers;

public class RepoHelper {

    public static String capFirstLetter(String getterStr) {

        if (getterStr == null || getterStr.isEmpty()) {
            return getterStr;
        }

        return getterStr.substring(0, 1).toUpperCase() + getterStr.substring(1).toLowerCase();
    }
}
