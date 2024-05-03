package com.example.project02_;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppRepository;
import com.example.project02_.recycler.ProductAndQuantityAdapter;

public class CartActivity extends AppCompatActivity {
    private AppRepository repository;
    private RecyclerView recyclerView;
    private ProductAndQuantityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        repository = AppRepository.getRepository(getApplication());

        recyclerView = findViewById(R.id.recyclerViewCart);
        adapter = new ProductAndQuantityAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        int userId = getIntent().getIntExtra("USER_ID", 1);
        repository.getProductsAndQuantitiesForUser(userId).observe(this, adapter::setItems);


    }
}