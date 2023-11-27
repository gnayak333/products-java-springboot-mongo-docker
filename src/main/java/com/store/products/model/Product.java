package com.store.products.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "stocks_sequence";

    @Id
    private Integer id;
    @NotNull(message = "Name is Mandatory")
    private String name;
    @NotNull(message = "CurrentPrice is Mandatory")
    private double currentPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdate;
}
