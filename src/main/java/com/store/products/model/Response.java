package com.store.products.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private Integer responseCode;
    private String message;
    private List<Product> productList;
    private Product product;
}
