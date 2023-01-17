package com.bilalachraf.billingsupplierservice.events;

import com.bilalachraf.billingsupplierservice.entities.Bill;
import com.bilalachraf.billingsupplierservice.entities.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class BillEvent {
    private Long id;
    private Bill bill;
    private Date createdAt;
}
