package com.bilalachraf.billingservice.services;

import com.bilalachraf.billingservice.entities.Bill;
import com.bilalachraf.billingservice.entities.ProductItem;
import com.bilalachraf.billingservice.events.BillEvent;
import com.bilalachraf.billingservice.repositories.BillRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class KafkaBillingService {
    @Autowired
    BillRepository billRepository;
    @Bean
    public Consumer<BillEvent> billEventConsumer() {
        return (input)-> {
            try {
                saveBill(input);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        };
    }
    private void saveBill(BillEvent billEvent) throws IOException {
//        if(!new File("storage").exists())
//            new File("storage").mkdir();
//        if(!new File("storage/bills.txt").exists())
//            new File("storage/bills.txt").createNewFile();
        Bill bill=new Bill();
        bill.setBillingDate(billEvent.getBillingDate());
        bill.setCustomerID(billEvent.getCustomerID());
        List<ProductItem> productItems=billEvent.getProductItems().stream().map(bE-> new ProductItem()).collect(Collectors.toUnmodifiableList());
        bill.setProductItems(productItems);
        Bill newBill=bill;
        //newBill=billRepository.save(bill);
        //FileWriter fos=new FileWriter("storage/bills.txt",true);
        //fos.append(newBill.toString()).append("\n");
        //fos.close();

    }
}
