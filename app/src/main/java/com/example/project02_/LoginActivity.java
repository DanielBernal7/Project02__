package com.example.project02_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                            intent.putExtra("USER_NAME_KEY", username);
                            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putInt("userId", user.getId());
                            editor.putString("username", username);
                            editor.putBoolean("isAdmin", user.isAdmin());
                            editor.apply();
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "UserID/Password is incorrect try again", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }

}