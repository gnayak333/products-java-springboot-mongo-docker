package com.store.products.service;

import com.store.products.model.Product;
import com.store.products.model.ProductsSequence;
import com.store.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ProductsService {
    private static final String PRODUCTS_SEQUENCE = "products_sequence";
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    public List<Product> getProductsWithSize(Integer size) {
        Page<Product> stockPage = productsRepository.findAll(Pageable.ofSize(size));
        return stockPage.toList();
    }

    public void createProduct(Product product) {
        product.setId(generateSequence());
        product.setLastUpdate(LocalDateTime.now());
        productsRepository.save(product);
    }

    public Optional<Product> getOneProduct(Integer id) {
        return productsRepository.findById(id);
    }

    public void updateProduct(Product product, Integer id) {
        Optional<Product> stockOptional = getOneProduct(id);
        if (stockOptional.isPresent()) {
            Product productToUpdate = stockOptional.get();
            productToUpdate.setCurrentPrice(product.getCurrentPrice());
            productToUpdate.setLastUpdate(LocalDateTime.now());
            productsRepository.save(productToUpdate);
        }
    }

    public void deleteOneProduct(Integer id) {
        productsRepository.deleteById(id);
    }

    public Boolean productExists(Integer id) {
        return productsRepository.existsById(id);
    }

    public Boolean productExistsByName(String name) {
        return !productsRepository.findByName(name).isEmpty();
    }

    public Integer generateSequence() {
        ProductsSequence counter = mongoOperations.findAndModify(query(where("_id").is(PRODUCTS_SEQUENCE)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                ProductsSequence.class);
        return Math.toIntExact(!Objects.isNull(counter) ? counter.getSeq() : 1);
    }

    public void setSequence(Integer seq) {
        mongoOperations.findAndModify(query(where("_id").is(PRODUCTS_SEQUENCE)),
                new Update().set("seq", seq), ProductsSequence.class);
    }
}
