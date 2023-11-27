package com.store.products.Utils;

import com.store.products.model.Product;

import java.time.LocalDateTime;

public class TestUtils {

    public static Product getProduct() {
        Product product = new Product();
        product.setName("Iphone 13");
        product.setCurrentPrice(189.01);
        product.setLastUpdate(LocalDateTime.now());

        return product;
    }
}
