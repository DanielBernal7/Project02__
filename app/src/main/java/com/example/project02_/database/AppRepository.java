package com.example.project02_.database;

import android.content.Context;

import com.example.project02_.database.entities.User;

import java.util.List;

public class AppRepository {
    private UserDAO userDAO;
    private List<User> allUsers;

    public AppRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        userDAO = db.userDAO();
        allUsers = userDAO.getAllUsers();
    }

    public List<User> getAllUsers(){
        return userDAO.getAllUsers();
    }

}
