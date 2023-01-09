package com.bilalachraf.billingservice.services;

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
import java.util.function.Consumer;

@Service
public class KafkaBillingService {
//    @Autowired
//    BillRepository billRepository;
    @Bean
    public Consumer<BillEvent> billEventConsumer() {
        return (input)-> {
            try {
                saveBillToFile(input);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        };
    }
    private void saveBillToFile(BillEvent billEvent) throws IOException {
//        if(!new File("storage").exists())
//            new File("storage").mkdir();
//        if(!new File("storage/bills.txt").exists())
//            new File("storage/bills.txt").createNewFile();
        FileWriter fos=new FileWriter("storage/bills.txt",true);
        fos.append(billEvent.toString()).append("\n");
        fos.close();

    }
}
