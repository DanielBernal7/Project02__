package com.example.project02_.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02_.database.embedded.ProductAndQuantity;
import com.example.project02_.database.entities.Cart;
import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart... cart);

    @Delete
    void deleteCartItem(Cart cart);

    @Update
    void updateCartItem(Cart cart);

    @Query("SELECT * FROM cart WHERE userId = :userId")
    LiveData<List<Cart> > getCartItemsForUser(int userId);

    @Query("SELECT p.* FROM products p INNER JOIN cart c ON p.id = c.productId WHERE c.userId = :userId")
    LiveData<List<Product>> getProductsForUser(int userId);

    @Insert
    void insertIntoCart(Cart cart);

    @Delete
    void removeFromCart(Cart cart);


    @Query("DELETE from cart")
    void deleteAll();

    @Query("SELECT p.*, c.quantity FROM products p INNER JOIN cart c ON p.id = c.productId WHERE c.userId = :userId")
    LiveData<List<ProductAndQuantity>> getProductsAndQuantitiesForUser(int userId);

    @Query("SELECT * FROM cart WHERE userId = :userId AND productId = :productId LIMIT 1")
    Cart getCartItemForUserAndProduct(int userId, int productId);


}
