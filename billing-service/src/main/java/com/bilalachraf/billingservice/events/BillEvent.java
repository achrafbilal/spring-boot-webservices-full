package com.bilalachraf.billingservice.events;

import com.bilalachraf.billingservice.entities.Bill;
import com.bilalachraf.billingservice.entities.ProductItem;
import com.bilalachraf.billingservice.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class BillEvent {
    private Long id;
    private Date billingDate;
    private Collection<ProductItemEvent> productItems;
    private Long customerID;
    private Date createdAt;
}
