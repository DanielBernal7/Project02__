package com.example.project02_.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project02_.database.entities.Cart;
import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Product.class, Cart.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();
    public abstract CartDAO cartDAO();


    public static final String USER_TABLE = "users";
    private static final String DATABASE_NAME = "AppDatabase";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i("CreateDatabase", "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);

                ProductDAO dao2 = INSTANCE.productDAO();
                dao2.deleteAll();
                Product testProduct = new Product("TestProduct", 30.00, "This is a test product", "TV");
                Product testProduct2 = new Product("TestProduct2", 40.00, "This is a test product", "Phone");
                Product prod2 = new Product("TestProduct2", 30.00, "This is a test product", "TV");
                Product prod3 = new Product("TestProduct3", 30.00, "This is a test product", "TV");
                Product prod4 = new Product("TestProduct4", 30.00, "This is a test product", "TV");
                Product prod5 = new Product("TestProduct5", 30.00, "This is a test product", "TV");
                Product prod6 = new Product("TestProduct6", 30.00, "This is a test product", "TV");
                Product prod7 = new Product("TestProduct7", 30.00, "This is a test product", "TV");
                Product prod8 = new Product("TestProduct8", 30.00, "This is a test product", "TV");
                Product prod9 = new Product("TestProduct9", 30.00, "This is a test product", "TV");
                Product prod10 = new Product("TestProduct10", 30.00, "This is a test product", "TV");
                Product prod11 = new Product("TestProduct11", 30.00, "This is a test product", "TV");
                Product prod12 = new Product("TestProduct12", 30.00, "This is a test product", "TV");

                dao2.insert(testProduct);
                dao2.insert(testProduct2);
                //
                dao2.insert(prod2);
                dao2.insert(prod3);
                dao2.insert(prod4);
                dao2.insert(prod5);
                dao2.insert(prod6);
                dao2.insert(prod7);
                dao2.insert(prod8);
                dao2.insert(prod9);
                dao2.insert(prod10);
                dao2.insert(prod11);
                dao2.insert(prod12);
            });
        }
    };



}
