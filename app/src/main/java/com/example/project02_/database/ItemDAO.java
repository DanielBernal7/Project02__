// ItemDAO.java
package com.example.project02_.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project02_.database.entities.Item;

import java.util.List;

@Dao
public interface ItemDAO {
    @Insert
    void insert(Item item);

    @Query("SELECT * FROM items")
    LiveData<List<Item>> getAllItems();

    @Query("DELETE FROM items")
    void deleteAll(); // Add this method to delete all items
}