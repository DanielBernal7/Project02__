// MainActivity.java
package com.example.project02_;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppDatabase;
import com.example.project02_.database.entities.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this);
        recyclerView.setAdapter(itemAdapter);

        populateDatabase();
        populateRecyclerView();
    }

    private void populateDatabase() {
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                String name = "Item " + i;
                double price = Math.random() * 100;
                String description = "Description for Item " + i;
                Item item = new Item(name, price, description);
                AppDatabase.getInstance(this).itemDAO().insert(item);
            }
        }).start();
    }

    private void populateRecyclerView() {
        AppDatabase.getInstance(this).itemDAO().getAllItems().observe(this, items -> {
            Log.d(TAG, "Number of items retrieved: " + items.size());
            itemAdapter.setItemList(items);
        });
    }
}