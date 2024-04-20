package com.example.project02_.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

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

    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        userDAO.getUserById(1);
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



    public void insertUser(User... user){
        AppDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }


    // LiveData method to get all users
//    public LiveData<List<User>> getAllUsers() {
//        return userDAO.get();
//    }

//    public void insertTestData() {
//        executor.execute(() -> {
//            userDAO.insert(new User("testuser1", "testpass1", false));
//            userDAO.insert(new User("admin2", "adminpass2", true));
//        });
//    }
}
