package com.example.project02_;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.project02_.database.AppDatabase;
import com.example.project02_.database.AppRepository;
import com.example.project02_.database.ProductDAO;
import com.example.project02_.database.UserDAO;
import com.example.project02_.database.entities.Product;
import com.example.project02_.database.entities.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AppRepositoryTest {
    @Mock
    private UserDAO userDAO;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private AppRepository repository;

    @Mock
    private Application mockApplication;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = mock(AppRepository.class);
    }

    @Test
    public void testProductInsertion() {
        Product product = new Product("Berekly Sand Worm", 10.99, "Description", "Type");
        when(repository.insertProduct(product)).thenReturn(1L);
        long productId = repository.insertProduct(product);
        assertEquals(1, productId);
        verify(repository).insertProduct(product);
    }

    @Test
    public void testProductDeletion() {
        String productName = "Berekly Sand Worm";
        repository.deleteProductByName(productName);
        verify(repository).deleteProductByName(productName);
    }


    @Test
    public void testGetUserByUsername() throws Exception {
        //We create a test user
        User expectedUser = new User("testUser", "testPassword");
        when(repository.getUserByUsernameDirectly("testUser")).thenReturn(expectedUser);


        User actualUser = repository.getUserByUsernameDirectly("testUser");

        //First we make sure that the user is not null
        assertNotNull("User should not be null", actualUser);
        assertEquals("Expected username does not match", "testUser", actualUser.getUsername());


        verify(repository).getUserByUsernameDirectly("testUser");
    }






}
