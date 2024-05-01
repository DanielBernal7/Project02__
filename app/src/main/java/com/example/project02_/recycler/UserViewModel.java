package com.example.project02_.recycler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.User;

import java.util.List;

public class UserViewModel extends ViewModel {
    private final AppRepository repository;

    public UserViewModel(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public LiveData<User> getUserByUserName(String username) {
        return repository.getUserByUserName(username);
    }
}