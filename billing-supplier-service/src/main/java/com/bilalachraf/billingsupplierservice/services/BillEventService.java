package com.bilalachraf.billingsupplierservice.services;

import com.bilalachraf.billingsupplierservice.entities.Bill;
import com.bilalachraf.billingsupplierservice.entities.ProductItem;
import com.bilalachraf.billingsupplierservice.events.BillEvent;
import com.bilalachraf.billingsupplierservice.events.ProductItemEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class BillEventService {



//	@Bean
//	public Consumer<BillEvent> pageEventConsumer(){
//		return (input)->{
//			System.out.println("********");
//			System.out.println(input.toString());
//			System.out.println("********");
//		};
//	}

	private Integer getRandom(Integer bound){
		Random random=new Random();
		return random.nextInt(bound);
	}

	@Bean
	public Supplier<BillEvent> billEventSupplier(){
		return this::generateBill;
	}
	private BillEvent generateBill(){

		List<ProductItemEvent> productItems=new ArrayList<>();
		for (int i = 0; i < getRandom(4)+1; i++) {
			productItems.add(
					new ProductItemEvent(
							getRandom(1000),
							(long) getRandom(10),
							(double) ((getRandom(20) * 1000) + 1)
					)
			);
		}
		return new BillEvent(
				getRandom(1000)+1L,
				new Date(new Date().toInstant().minus(getRandom(365), ChronoUnit.DAYS).toEpochMilli()),
				productItems,
				getRandom(10)+1L,
				new Date()
		);
	}

//	@Bean
//	public Function<BillEvent,BillEvent> pageEventFunction(){
//		return (input)->{
//			input.setCreatedAt(new Date(new Date().toInstant().minus(new Random().nextInt(365), ChronoUnit.DAYS).toEpochMilli()));
//			return input;
//		};
//	}

//	@Bean
//	public Function<KStream<String,BillEvent>,KStream<String,Long>> kStreamFunction(){
//		return (input)-> {
//			KStream<String,Long> result=input
//					.filter((k,v)->v.getBill().getProductItems().size()>1)
//					.map((k,v)->new KeyValue<>(v.getBill().getCustomerID(),0L))
//					.groupBy((k,v)->k,Grouped.with(Serdes.Long(),Serdes.Long()))
//					.windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofDays(365)))
//					.count(Materialized.as("products-count"))
//					.toStream()
//					.map(
//						(k,v)->{
//							KeyValue <String,Long> kv=new KeyValue<>(
//								k.window().startTime()+" => "+k.window().endTime()+" "+k.key(),
//								v
//							);
//							System.out.println(kv.key+" "+kv.value);
//							return kv;
//						}
//					);
//			return  result;
//		};
//	}
}
