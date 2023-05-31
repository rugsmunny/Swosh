package org.example.Controllers;

import org.example.Models.User;
import org.example.Repositories.UserRepository;


public class UserController {

    public void updateLoginStatus(User user) {

      new UserRepository().setUserLoginStatus(user);

    }
    public void createAccount(User user) {

        new UserRepository().createAccount(user);

    }

    public boolean deleteAccount(User user) {

       return new UserRepository().deleteUserAccount(user);

    }

    public User changeCredentials(User user, String target){
        return new UserRepository().updateCredentials(user, target);
    }


}
