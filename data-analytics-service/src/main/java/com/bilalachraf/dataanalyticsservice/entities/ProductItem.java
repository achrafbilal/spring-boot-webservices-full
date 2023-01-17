package com.bilalachraf.dataanalyticsservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ProductItem {
    private Long id;
    private Integer quantity;
    private Long productID;
    private Double price;
}
