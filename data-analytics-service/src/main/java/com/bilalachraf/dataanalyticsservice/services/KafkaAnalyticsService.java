package com.bilalachraf.dataanalyticsservice.services;

import com.bilalachraf.dataanalyticsservice.events.BillEvent;
import com.bilalachraf.dataanalyticsservice.events.ProductItemEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
public class KafkaAnalyticsService {

    private void print(KStream<Long,Long> result){
        result.foreach((k,v)->{
            System.out.println(k.toString()+" => "+v.toString());
        });
    }
    private KStream<Long,Long> getResult(KStream<String,BillEvent> input){
        System.out.println(input);
        KStream<Long,Long> result=input
        //.filter((k,v)-> Instant.now().minus(1,ChronoUnit.YEARS).isBefore(v.getBillingDate().toInstant()))//v.getBillingDate().toInstant().minus(1, ChronoUnit.YEARS)>)
        .map((k,v)->new KeyValue<Long,Long>(v.getBill().getCustomerID(),0L))
        .groupBy((k,v)->k, Grouped.with(Serdes.Long(),Serdes.Long()))
        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(60)))
        .count(Materialized.as("bill-count"))
        .toStream()
        .map(
            (k,v)->{
                KeyValue <Long,Long> kv=new KeyValue<>(
                    k.key(),
                    v
                );
                return kv;
            }
        );
        print(result);
        return  result;
    }
    @Bean
    public Function<KStream<String,BillEvent>,KStream<Long,Long>> kStreamFunction(){
        return this::getResult;
    }

}
