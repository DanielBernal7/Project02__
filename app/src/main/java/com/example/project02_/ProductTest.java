package com.example.project02_;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.Product;
import com.example.project02_.recycler.ProductAdapter;

import java.util.List;

public class ProductTest extends AppCompatActivity {

    private AppRepository repository;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_test);

        repository = AppRepository.getRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(this, null, repository);
        recyclerView.setAdapter(adapter);

        // Observe the LiveData for products and update the adapter when it changes
        repository.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                adapter.setProductList(productList);
            }
        });
    }
}