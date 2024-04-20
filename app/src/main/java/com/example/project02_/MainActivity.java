package com.example.project02_;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;

import com.example.project02_.database.AppDatabase;
import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {
    private AppRepository appRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getDatabase(this.getApplicationContext());
        Executors.newSingleThreadExecutor().execute(()->{
            appRepository = new AppRepository(getApplicationContext());
        });

    }


}