package org.example;

import org.example.DataBase.DbTablesAndColumnsInitializer;
import org.example.Views.UserView;

public class Main {

    public static void main(String[] args)  {

        new DbTablesAndColumnsInitializer();
        new UserView().loginPage();

    }
}




