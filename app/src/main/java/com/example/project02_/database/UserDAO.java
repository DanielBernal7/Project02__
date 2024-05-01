package com.example.project02_.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project02_.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE from users WHERE id = :id")
    void deleteUserById(int id);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    @Query("DELETE from " + AppDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();


    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsernameDirectly(String username);
}
