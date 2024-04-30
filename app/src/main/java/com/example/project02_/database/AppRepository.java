package com.example.project02_.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        userDAO.getUserById(1);
        this.productDAO = db.productDAO();
    }

    public static AppRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );

        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d("Trial", "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public User getUserByUsernameDirectly(String username) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<User> future = executor.submit(() -> userDAO.getUserByUsernameDirectly(username));

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void insertUser(User... user){
        AppDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }

    public void insertProduct(Product... product){
        AppDatabase.databaseWriteExecutor.execute(()->{
            productDAO.insert(product);
        });
    }

}
