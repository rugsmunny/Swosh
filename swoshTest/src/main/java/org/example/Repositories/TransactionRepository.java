package org.example.Repositories;

import org.example.DataBase.QueryBuilder;
import org.example.Helpers.DbHelper;
import org.example.Models.Transaction;
import org.example.Models.User;
import org.example.Views.UserView;

import java.lang.reflect.Method;

import static org.example.Helpers.RepoHelper.capFirstLetter;
import static org.example.Helpers.ViewHelper.getUserInput;


public class TransactionRepository {

    private static DbHelper dbH;
    QueryBuilder qb = new QueryBuilder();



    public TransactionRepository() {
        dbH = DbHelper.getInstance();
    }


/*    public void createTransaction(Transaction tran) {

        qb.createTransaction(tran);

    }

    public void getTransactions(Transaction tran) {

        qb.getTransactions(tran);

    }*/

}

