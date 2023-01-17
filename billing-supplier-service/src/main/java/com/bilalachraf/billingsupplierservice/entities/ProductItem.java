package com.bilalachraf.billingsupplierservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import scala.Product;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class ProductItem {
    private Long id;
    private Integer quantity;
    private Long productID;
    private Double price;
}
