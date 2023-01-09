package com.bilalachraf.billingsupplierservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductItemEvent {
    private Integer quantity;
    private Long productID;
    private Double price;
}
