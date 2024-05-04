package com.example.project02_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;
import com.example.project02_.databinding.ActivityLandingPageBinding;
import com.example.project02_.recycler.ProductAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LandingPage extends AppCompatActivity {
    private ActivityLandingPageBinding binding;
    private AppRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
        Button adminB = findViewById(R.id.adminButton);
        Button logoutB = findViewById(R.id.logoutButton);


        Intent intent = getIntent();

        TextView usernameTextView = findViewById(R.id.usernameTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "user");
        if(!username.isEmpty()){
            usernameTextView.setText(username);
        }

        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        if(!isAdmin){
            adminB.setVisibility(View.INVISIBLE);
        } else {
            adminB.setVisibility(View.VISIBLE);
        }


        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("isLoggedIn");
                editor.remove("username");
                editor.apply();

                Intent intent = new Intent(LandingPage.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Button bSearch = findViewById(R.id.searchButton);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, ProductTest.class);
                startActivity(intent);
            }
        });

        Button bAdmin = findViewById(R.id.adminButton);
        bAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage.this, AdminPageActivity.class);
                startActivity(intent);
            }
        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(()->{
            // Check if the user and product exist before inserting into the cart
            User user = repository.getUserById(1);
            Product product = repository.getProductById(1);
        });

        //This is testing for the cart table and adding items to the cart
        User user = repository.getUserByUserName("username").getValue();
        int userId = getUserId();
//        if(userId != -1){
//
//        }
        List<Integer> productIds = Arrays.asList(1);
        List<Integer> quantities = Arrays.asList(3);
        repository.addItemsToCart(userId, productIds, quantities);

        Button bCart = findViewById(R.id.cartButton);
        bCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("userId", userId);
                editor.apply();
                Intent intent = new Intent(LandingPage.this, CartActivity.class);

                intent.putExtra("userId", userId);

                startActivity(intent);
            }
        });

    }

    private int getUserId() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return prefs.getInt("userId", -1);
    }
}