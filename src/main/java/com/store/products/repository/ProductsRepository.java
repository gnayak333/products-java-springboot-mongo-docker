package com.store.products.repository;

import com.store.products.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductsRepository extends MongoRepository<Product, Integer> {
    List<Product> findByName(String name);

    void deleteByName(String name);
}
