package com.example.project02_;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.User;
import com.example.project02_.recycler.UserAdapter;
import com.example.project02_.recycler.UserViewModel;
import com.example.project02_.recycler.UserViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class AdminPageActivity extends AppCompatActivity {
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        AppRepository repository = AppRepository.getRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> emptyList = new ArrayList<>();
        UserAdapter adapter = new UserAdapter(emptyList, user -> deleteUser(user, repository));
        recyclerView.setAdapter(adapter);



        UserViewModel viewModel = new ViewModelProvider(this, new UserViewModelFactory(repository)).get(UserViewModel.class);

        viewModel.getAllUsers().observe(this, users -> adapter.updateUsers(users));

        EditText serachEditText = findViewById(R.id.searchEditText);
        serachEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //I don't think there needs to be anything here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Or here
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void deleteUser(User user, AppRepository repository){
        if(repository != null){
            repository.deleteUser(user);
        } else {
            System.out.println("Repository is null");
        }
    }
}