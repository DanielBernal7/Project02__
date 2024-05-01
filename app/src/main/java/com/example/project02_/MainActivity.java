// MainActivity.java
package com.example.project02_;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppDatabase;
import com.example.project02_.database.entities.Item;

import java.util.ArrayList;
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
            // Delete all existing items from the database
            AppDatabase.getInstance(this).itemDAO().deleteAll();

            List<Item> items = createUniqueItems(); // Create a list of unique items
            for (Item item : items) {
                AppDatabase.getInstance(this).itemDAO().insert(item); // Insert each item into the database
            }
        }).start();
    }

    private void populateRecyclerView() {
        AppDatabase.getInstance(this).itemDAO().getAllItems().observe(this, items -> {
            Log.d(TAG, "Number of items retrieved: " + items.size());
            itemAdapter.setItemList(items);
        });
    }
    private List<Item> createUniqueItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Smartwatch", 99.99, "Stay connected and track your fitness with this sleek smartwatch."));
        items.add(new Item("Wireless Earbuds", 49.99, "Enjoy crystal-clear sound and wireless freedom with these Bluetooth earbuds."));
        items.add(new Item("Laptop Backpack", 39.99, "Carry your laptop and essentials with ease in this durable backpack."));
        items.add(new Item("Wireless Charging Pad", 29.99, "Charge your compatible devices wirelessly with this convenient charging pad."));
        items.add(new Item("Resistance Bands Set", 19.99, "Work out from home with this versatile set of resistance bands."));
        items.add(new Item("Nuclear Bomb", 999.99, "Demolish your enemies with the power of the atom. 100% satisfaction guaranteed!"));
        items.add(new Item("Bookshelf", 34.69, "A nice place to store all your books or other objects of interest that you may have."));
        items.add(new Item("Pillow 2-Pack", 5.99, "2 Pillows for the price of one! What a comforting deal!"));
        items.add(new Item("Plastic Food Containers set", 30.99, "An entire set of containers to hold your food storage, coming in a variety of sizes."));
        items.add(new Item("Ugly Stik 8' Fishing Pole", 80.99, "The toughest and most durable rod in america. No fish is un-catchable with this in your hands."));

        return items;
    }
}

