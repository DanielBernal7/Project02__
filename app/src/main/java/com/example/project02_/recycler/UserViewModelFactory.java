package com.example.project02_.recycler;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.project02_.database.AppRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private final AppRepository repository;

    public UserViewModelFactory(AppRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}