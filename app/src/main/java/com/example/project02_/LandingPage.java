package com.example.project02_;

import android.content.Intent;
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


        Intent intent = getIntent();

        TextView usernameTextView = findViewById(R.id.usernameTextView);
        boolean isAdmin = intent.getBooleanExtra("IS_ADMIN_KEY", true);

        if(isAdmin){
            adminB.setVisibility(View.INVISIBLE);
        }

        String username = intent.getStringExtra("USER_NAME_KEY");
        if(username != null && !username.isEmpty()){
            usernameTextView.setText(username);
        }





    }
}