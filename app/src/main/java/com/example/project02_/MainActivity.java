package com.example.project02_;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.SQLiteCopyOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.project02_.database.AppDatabase;
import com.example.project02_.database.AppRepository;
import com.example.project02_.database.UserDAO;
import com.example.project02_.database.entities.User;
import com.example.project02_.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    private AppRepository repository;

    private ActivityMainBinding binding;

    private AppDatabase db;
    private static int loggedIn  = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        Button logIn = findViewById(R.id.loginButtonInFrontPage);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

//        db = AppDatabase.getDatabase(getApplicationContext());
//        UserDAO userDao = db.userDAO();
//        userDao.insertUser(new User("testuser", "password", false));


//        AppDatabase db = AppDatabase.getDatabase(this.getApplicationContext());

//        appRepository = AppRepository.getInstance(this.getApplicationContext());
//        appRepository.insertTestData();

    }


}