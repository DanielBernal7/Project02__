package com.example.project02_.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project02_.database.embedded.ProductAndQuantity;
import com.example.project02_.database.entities.Cart;
import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppRepository {
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final CartDAO cartDAO;

    private static AppRepository repository;

    private AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        userDAO.getUserById(1);
        this.productDAO = db.productDAO();
        this.cartDAO = db.cartDAO();
    }

    public static AppRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<AppRepository> future = AppDatabase.databaseWriteExecutor.submit(
                new Callable<AppRepository>() {
                    @Override
                    public AppRepository call() throws Exception {
                        return new AppRepository(application);
                    }
                }
        );

        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d("Trial", "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public User getUserByUsernameDirectly(String username) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<User> future = executor.submit(() -> userDAO.getUserByUsernameDirectly(username));

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void insertUser(User... user){
        AppDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }

    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

    public void insertProduct(Product... product){
        AppDatabase.databaseWriteExecutor.execute(()->{
            productDAO.insert(product);
        });
    }

    public Product getProductByName(String name){
        return productDAO.getProductByName(name);
    }

    public void deleteUser(User user){
        Executors.newSingleThreadExecutor().execute(()-> userDAO.deleteUser(user));
    }


    //--------------------------------- Cart ------------------------------------------
    public LiveData<List<Cart>> getCartItemsForUser(int userId) {
        return cartDAO.getCartItemsForUser(userId);
    }

    public void insertCartItem(Cart... cartItems) {
        AppDatabase.databaseWriteExecutor.execute(() -> cartDAO.insert(cartItems));
    }

    public void deleteCartItem(Cart cartItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> cartDAO.deleteCartItem(cartItem));
    }

    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    public User getUserById(int userId) {
        return userDAO.getUserByIdDirectly(userId);
    }

    public void addItemsToCart(int userId, List<Integer> productId, List<Integer> quantity){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            User user = userDAO.getUserByIdDirectly(userId);
            if(user != null){
                for(int i = 0; i < productId.size(); i++){
                    Product product = productDAO.getProductById(productId.get(i));
                    if(product != null){
                        Cart cartItem = new Cart(user.getId(), product.getId(), quantity.get(i));
                        cartDAO.insert(cartItem);
                    }
                }
            }
            executor.shutdown();
        });
    }
    public LiveData<List<ProductAndQuantity>> getProductsAndQuantitiesForUser(int userId) {
        return cartDAO.getProductsAndQuantitiesForUser(userId);
    }

}
