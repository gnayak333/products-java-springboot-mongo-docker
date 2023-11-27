package com.store.products.controller;

import com.store.products.model.Product;
import com.store.products.service.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) Integer size) {
        if (size != null && size < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Product> productsList = size != null ? productsService.getProductsWithSize(size) : productsService.getProducts();
        if (productsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<HttpStatus> createProduct(@Valid @RequestBody Product product) {
        if (productsService.productExistsByName(product.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        productsService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Product> getOneProduct(@PathVariable(value = "id") Integer id) {
        Optional<Product> productOptional = productsService.getOneProduct(id);

        return productOptional.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<HttpStatus> updateOneProduct(@PathVariable(value = "id") Integer id, @RequestBody Product product) {
        if (!productsService.productExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productsService.updateProduct(product, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<HttpStatus> deleteOneProduct(@PathVariable(value = "id") Integer id) {
        if (!productsService.productExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productsService.deleteOneProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
