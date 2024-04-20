package com.example.project02_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project02_.database.AppRepository;
import com.example.project02_.databinding.ActivityLandingPageBinding;

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
        boolean isAdmin = intent.getBooleanExtra("IS_ADMIN_KEY", true);

        if(isAdmin){
            adminB.setVisibility(View.INVISIBLE);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "user");
//        String username = intent.getStringExtra("USER_NAME_KEY");
        if(!username.isEmpty()){
            usernameTextView.setText(username);
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

    }
}