package com.example.project02_;

import android.os.Bundle;

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
    AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> emptyList = new ArrayList<>();
        UserAdapter adapter = new UserAdapter(emptyList);
        recyclerView.setAdapter(adapter);

        AppRepository repository = AppRepository.getRepository(getApplication());

        UserViewModel viewModel = new ViewModelProvider(this, new UserViewModelFactory(repository)).get(UserViewModel.class);

        viewModel.getAllUsers().observe(this, users -> adapter.updateUsers(users));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}