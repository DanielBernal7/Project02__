package com.example.project02_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.User;
import com.example.project02_.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    AppRepository repository;
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        repository = AppRepository.getRepository(getApplication());

        EditText editTextUsername = findViewById(R.id.EditTextViewUsername);
        EditText editTextPassword = findViewById(R.id.EditTextPassword);

        Button bCreate = findViewById(R.id.buttonCreate);

        bCreate.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            User existingUser = repository.getUserByUsernameDirectly(username);
            if (existingUser == null) {
                User newUser = new User(username, password);
                long userId = repository.insertUser(newUser);

                if(userId != -1) {
                    SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putBoolean("isAdmin", newUser.isAdmin());
                    editor.putString("username", username);
                    editor.putInt("userId", (int) userId);
                    editor.apply();

                    Intent intent = new Intent(SignUpActivity.this, LandingPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this, "Username is already being used", Toast.LENGTH_SHORT).show();
            }
        });

//        bCreate.setOnClickListener(v -> {
//            String username = editTextUsername.getText().toString();
//            String password = editTextPassword.getText().toString();
//
//            // Observe LiveData to check if the user exists
//            repository.getUserByUserName(username).observe(this, user -> {
//                if (user == null) {
//                    // User does not exist, proceed to add
//                    User newUser = new User(username, password);
//                    repository.insertUser(newUser);
//
//                    SharedPreferences preferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putBoolean("isLoggedIn", true);
//                    editor.putString("username", username);
//                    editor.apply();
//                    Intent intent = new Intent(SignUpActivity.this, LandingPage.class);
//                    startActivity(intent);
//
//                    //This was for debug purposes
////                    Toast.makeText(SignUpActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    // User exists, show a toast
//                    Toast.makeText(SignUpActivity.this, "Username is already being used", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });


//
//        bCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = editTextUsername.getText().toString();
//                String password = editTextPassword.getText().toString();
//
//                if(repository.getUserByUserName(username) == null){
//                    User newUser = new User(username, password);
//                    repository.insertUser(newUser);
//                } else {
//                    Toast.makeText(SignUpActivity.this, "Username is already being used", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//

    }
}