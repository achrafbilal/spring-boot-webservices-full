package com.bilalachraf.dataanalyticsservice.events;

import com.bilalachraf.dataanalyticsservice.entities.Bill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class BillEvent {
    private Long id;
    private Bill bill;
    private Date createdAt;
}
