package com.example.project02_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.User;
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
                LiveData<User> userObserver = repository.getUserByUserName(username);
                userObserver.observe(LoginActivity.this,user -> {
                    if(user != null){
                        if(password.equals(user.getPassword()) && username.equals(user.getUsername())){
                            Intent intent = new Intent(LoginActivity.this, LandingPage.class);
                            if(user.isAdmin()){
                                intent.putExtra("IS_ADMIN_KEY", true);
                            }
                            intent.putExtra("USER_NAME_KEY", username);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

    }

}