package com.example.project02_.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.project02_.database.embedded.ProductAndQuantity;
import com.example.project02_.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product... product);

    @Query("SELECT * FROM products WHERE id = :id")
    Product geProductById(int id);

    @Query("SELECT * FROM products WHERE type = :type")
    Product getProductByType(int type);

    @Query("SELECT * FROM products WHERE name = :name")
    Product getProductByName(String name);

    @Query("DELETE from products")
    void deleteAll();

    @Query("SELECT * FROM products WHERE id = :id")
    Product getProductById(int id);


    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

}
