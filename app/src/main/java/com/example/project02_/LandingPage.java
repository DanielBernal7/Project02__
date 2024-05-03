package com.example.project02_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.Cart;
import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;
import com.example.project02_.databinding.ActivityLandingPageBinding;

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

//
//        LiveData<List<Cart>> cartItemsLiveData = repository.getCartItemsForUser(userId.getId());
//        cartItemsLiveData.observe(this, cartItems -> {
//            // Handle the list of cart items
//        });
//
//        // Insert a new cart item
//        Cart newCartItem = new Cart(userId.getId(), productId.getId());
//        repository.insertCartItem(newCartItem);



    }

    public void addItemsToCart(int userId, List<Integer> productIds, List<Integer> quantities) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            User user = repository.getUserById(userId);
            if (user != null) {
                for (int i = 0; i < productIds.size(); i++) {
                    Product product = repository.getProductById(productIds.get(i));
                    if (product != null) {
                        Cart cartItem = new Cart();
                        cartItem.setUserId(userId);
                        cartItem.setProductId(product.getId());
                        cartItem.setQuantity(quantities.get(i));
                        repository.insertCartItem(cartItem);
                    }
                }
            }
            handler.post(() -> {
                // Optionally update UI here after items are added
            });
        });
    }

}