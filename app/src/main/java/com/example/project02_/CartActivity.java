package com.example.project02_;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.embedded.ProductAndQuantity;
import com.example.project02_.recycler.ProductAdapter;
import com.example.project02_.recycler.ProductAndQuantityAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private AppRepository repository;
    private RecyclerView recyclerView;
    private TextView totalPrice;
    private ProductAndQuantityAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        repository = AppRepository.getRepository(getApplication());

        recyclerView = findViewById(R.id.recyclerViewCart);
        adapter = new ProductAndQuantityAdapter(this, repository);
        totalPrice = findViewById(R.id.totalCartPrice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        int userId = getIntent().getIntExtra("userId", 1);
        repository.getProductsAndQuantitiesForUser(userId).observe(this, items -> {
            adapter.setItems(items);
            updateTotalPrice(items);
        });
    }

    private void updateTotalPrice(List<ProductAndQuantity> items){
        double total = 0.0;

        if(items != null){
            for(ProductAndQuantity item : items){
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        totalPrice.setText(String.format("Total: $%.2f", total));
    }
}