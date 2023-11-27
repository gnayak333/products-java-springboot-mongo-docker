package com.store.products.service;


import com.store.products.model.Product;
import com.store.products.repository.ProductsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.store.products.Utils.TestUtils.getProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@DataMongoTest
class ProductsServiceTest {

    @Mock
    ProductsRepository productsRepository;

    @Mock
    MongoOperations mongoOperations;

    @InjectMocks
    ProductsService productsService;

    @MockBean
    Page<Product> productPage;

    @Test
    @DisplayName("Test that Products are fetched from the database")
    void test1() {
        Mockito.when(productsRepository.findAll()).thenReturn(Collections.singletonList(getProduct()));

        List<Product> productList = productsService.getProducts();

        assertEquals(1, productList.size());
        assertEquals(getProduct().getName(), productList.get(0).getName());
    }

    @Test
    @DisplayName("Test that Products are fetched from the database with pagination")
    void test2() {
        Mockito.when(productsRepository.findAll(any(Pageable.class))).thenReturn(productPage);
        Mockito.when(productPage.toList()).thenReturn(List.of(getProduct()));

        List<Product> productList = productsService.getProductsWithSize(1);

        assertEquals(1, productList.size());
    }

    @Test
    @DisplayName("Test that product creation does save the product to the repository")
    void test3() {
        productsService.createProduct(getProduct());

        Mockito.verify(productsRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Test that Product price is updated as expected")
    void test4() {
        Mockito.when(productsRepository.findById(any())).thenReturn(Optional.of(getProduct()));

        Product updateProduct = getProduct();
        updateProduct.setCurrentPrice(300.00);
        productsService.updateProduct(updateProduct, 1);

        Mockito.verify(productsRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Test that product deletion deletes the product")
    void test5() {
        productsService.deleteOneProduct(1);

        Mockito.verify(productsRepository, Mockito.times(1)).deleteById(any());
    }
}