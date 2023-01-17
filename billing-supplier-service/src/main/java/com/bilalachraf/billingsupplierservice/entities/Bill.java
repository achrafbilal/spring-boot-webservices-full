package com.bilalachraf.billingsupplierservice.entities;

import com.bilalachraf.billingsupplierservice.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Bill {
    private Long id;
    private Date billingDate;
    private Collection<ProductItem> productItems;
    private Long customerID;

}