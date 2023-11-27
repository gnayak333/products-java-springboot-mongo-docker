package com.store.products.controller;

import com.store.products.model.Product;
import com.store.products.service.ProductsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.store.products.Utils.TestUtils.getProduct;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @InjectMocks
    ProductsController productsController;

    @Mock
    ProductsService productsService;

    @Test
    @DisplayName("Test that Invalid size to get products will return bad request")
    void test1() {

        ResponseEntity<List<Product>> response = productsController.getProducts(0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Test that NOT_FOUND response is returned for fetching products with empty database")
    void test2() {

        ResponseEntity<List<Product>> response = productsController.getProducts(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test that OK response is returned for fetching products with valid size")
    void test3() {
        Mockito.when(productsService.getProductsWithSize(any())).thenReturn(Collections.singletonList(getProduct()));

        ResponseEntity<List<Product>> response = productsController.getProducts(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testThatProductCreationReturnsSuccess() {

        ResponseEntity<HttpStatus> response = productsController.createProduct(getProduct());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

        @Test
    void testThatProductCreationReturnsConflictForDuplicate() {
        Mockito.when(productsService.productExistsByName(anyString())).thenReturn(true);

        ResponseEntity<HttpStatus> response = productsController.createProduct(getProduct());

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testThatGetOneProductReturnsSuccessForValidRequest() {
        Mockito.when(productsService.getOneProduct(any())).thenReturn(Optional.of(getProduct()));

        ResponseEntity<Product> response = productsController.getOneProduct(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testThatGetOneProductReturnsNotFoundForInvalidRequest() {
        Mockito.when(productsService.getOneProduct(any())).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productsController.getOneProduct(2);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testThatUpdateOfExistingProductReturnsSuccess() {
        Mockito.when(productsService.productExists(any())).thenReturn(true);

        ResponseEntity<HttpStatus> response = productsController.updateOneProduct(1, getProduct());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testThatUpdateOfNonExistingProductReturnsNotFound() {
        Mockito.when(productsService.productExists(any())).thenReturn(false);

        ResponseEntity<HttpStatus> response = productsController.updateOneProduct(1, getProduct());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testThatDeleteProductIsSuccessForExistingProduct() {
        Mockito.when(productsService.productExists(any())).thenReturn(true);

        ResponseEntity<HttpStatus> response = productsController.deleteOneProduct(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testThatDeleteProductFailsForNonExistingProduct() {
        Mockito.when(productsService.productExists(any())).thenReturn(false);

        ResponseEntity<HttpStatus> response = productsController.deleteOneProduct(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}