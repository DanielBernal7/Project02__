package com.example.project02_;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_.database.AppRepository;
import com.example.project02_.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private AppRepository repository;

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());


        EditText userNameEditText = findViewById(R.id.editTextSignInUserID);
        EditText passwordEditText = findViewById(R.id.loginPagePasswordEditText);
        Button login = findViewById(R.id.loginButtonForSignInPageW);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

            }
        });

    }
}