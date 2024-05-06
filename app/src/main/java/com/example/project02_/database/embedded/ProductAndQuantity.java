package com.example.project02_.database.embedded;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.example.project02_.database.entities.Product;

public class ProductAndQuantity {
    @Embedded
    public Product product;

    @ColumnInfo(name = "quantity")
    public int quantity;

    public ProductAndQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
