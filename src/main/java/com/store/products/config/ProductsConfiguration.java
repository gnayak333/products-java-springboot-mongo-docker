package com.store.products.config;

import com.store.products.model.Product;
import com.store.products.repository.ProductsRepository;
import com.store.products.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDateTime;

@EnableMongoRepositories(basePackageClasses = ProductsRepository.class)
@Configuration
public class ProductsConfiguration {

    @Autowired
    ProductsService productsService;

    @Bean
    CommandLineRunner commandLineRunner(ProductsRepository productsRepository) {
        return strings -> {
            productsRepository.save(new Product(1, "Iphone 11", 190.0, LocalDateTime.now()));
            productsRepository.save(new Product(2, "Iphone 12", 330.8, LocalDateTime.now()));
            productsRepository.save(new Product(3, "Iphone 13", 290.1, LocalDateTime.now()));
            productsService.setSequence(3);
        };
    }
}
