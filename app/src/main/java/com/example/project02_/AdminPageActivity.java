package com.example.project02_;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.Product;
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
        repository = AppRepository.getRepository(getApplication());

        findViewById(R.id.btnAddItem).setOnClickListener(v -> showAddItemDialog());
        findViewById(R.id.btnDeleteItem).setOnClickListener(v -> showDeleteItemDialog());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> emptyList = new ArrayList<>();
        UserAdapter adapter = new UserAdapter(emptyList, this::deleteUser);
        recyclerView.setAdapter(adapter);

        UserViewModel viewModel = new ViewModelProvider(this, new UserViewModelFactory(repository)).get(UserViewModel.class);
        viewModel.getAllUsers().observe(this, adapter::updateUsers);
    }

    private void deleteUser(User user) {
        if (repository != null) {
            repository.deleteUser(user);
        } else {
            System.out.println("Repository is null");
        }
    }

    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etPrice = dialogView.findViewById(R.id.etPrice);
        EditText etDescription = dialogView.findViewById(R.id.etDescription);
        EditText etType = dialogView.findViewById(R.id.etType);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString();
            double price = Double.parseDouble(etPrice.getText().toString());
            String description = etDescription.getText().toString();
            String type = etType.getText().toString();

            // Add the product to the database
            Product product = new Product(name, price, description, type);
            long insertedId = repository.insertProduct(product);
            if (insertedId != -1) {
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void showDeleteItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_delete_item, null);
        builder.setView(dialogView);

        EditText etProductName = dialogView.findViewById(R.id.etProductName);

        builder.setPositiveButton("Delete", (dialog, which) -> {
            String productName = etProductName.getText().toString();
            // Delete the product from the database
            repository.deleteProductByName(productName);
            Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}