package com.example.project02_;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project02_.database.AppRepository;

public class SearchActivity extends AppCompatActivity {
    private AppRepository repository;

    com.example.project02_.databinding.ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView sView = findViewById(R.id.searchViews);
        sView.setQueryHint("Search");






    }
}