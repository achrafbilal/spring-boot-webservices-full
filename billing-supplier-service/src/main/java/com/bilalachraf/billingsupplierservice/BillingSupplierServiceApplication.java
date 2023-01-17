package com.bilalachraf.billingsupplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BillingSupplierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingSupplierServiceApplication.class, args);
    }

}
