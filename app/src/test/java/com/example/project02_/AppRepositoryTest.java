package com.example.project02_;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.project02_.database.AppRepository;
import com.example.project02_.database.entities.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AppRepositoryTest {

    @Mock
    private AppRepository repository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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

}
