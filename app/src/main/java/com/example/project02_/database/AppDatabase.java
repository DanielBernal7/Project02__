package com.example.project02_.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project02_.database.entities.User;

@Database(entities = {User.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    // Define the DAOs
    public abstract UserDAO userDAO();

    private static volatile AppDatabase INSTANCE;

    // Callback to pre-populate the database
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // This is where we write the logic to pre-populate the database
            // Note: Database operations should be done on a separate thread
            // Here we use execSQL for simplicity
            db.execSQL("INSERT INTO users (username, password, isAdmin) VALUES ('testuser1', 'testuser1', 0);");
            db.execSQL("INSERT INTO users (username, password, isAdmin) VALUES ('admin2', 'admin2', 1);");
        }
    };

    // Method to get the singleton instance of the database
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addCallback(roomDatabaseCallback).fallbackToDestructiveMigration() // Add the callback here
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
