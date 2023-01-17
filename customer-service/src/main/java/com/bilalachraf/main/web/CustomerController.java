package com.bilalachraf.main.web;

import com.bilalachraf.main.entities.Customer;
import com.bilalachraf.main.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @GetMapping("/api/customers/random")
    public Customer getRandomCustomer(){
        List<Customer> customers=customerRepository.findAll();
        return customers.get(new Random().nextInt(customers.size()-1));
    }
}
