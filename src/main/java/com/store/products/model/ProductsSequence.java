package com.store.products.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class ProductsSequence {
    @Id
    private String id;

    private Integer seq;
}
