package com.bilalachraf.billingsupplierservice.services;

import com.bilalachraf.billingsupplierservice.entities.Bill;
import com.bilalachraf.billingsupplierservice.entities.ProductItem;
import com.bilalachraf.billingsupplierservice.events.BillEvent;
import com.bilalachraf.billingsupplierservice.feign.CustomerRestClient;
import com.bilalachraf.billingsupplierservice.feign.ProductRestClient;
import com.bilalachraf.billingsupplierservice.model.Customer;
import com.bilalachraf.billingsupplierservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Service
public class BillEventService {

	@Autowired
	CustomerRestClient customerRestClient;
	@Autowired
	ProductRestClient productRestClient;
	private static Long billCount =1L;
	private static Long productItemCount =1L;


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
		Customer customer=customerRestClient.getRandomCustomer();
		List<Product> products=productRestClient.getRandomPageProducts();
		if(products.size()==0)
			throw new RuntimeException("Product items length is 0");
		List<ProductItem> productItems=new ArrayList<>();
		Bill bill=new Bill();
		bill.setId(billCount++);
		bill.setBillingDate(new Date(new Date().toInstant().minus(getRandom(365), ChronoUnit.DAYS).toEpochMilli()));
		bill.setCustomerID(customer.getId());
		Random random=new Random();
		for (int i = 0; i < products.size(); i++){
			ProductItem productItem=new ProductItem();
			productItem.setId(productItemCount++);
			productItem.setProductID(products.get(i).getId());
			productItem.setPrice((double) Math.round(random.nextDouble()*random.nextInt(1000)));
			productItem.setQuantity(random.nextInt(500));
			productItems.add(productItem);
		}
		bill.setProductItems(productItems);
		BillEvent billEvent=new BillEvent(
				getRandom(1000)+1L,
				bill,
				new Date()
		);
		System.out.println(billCount);
		return billEvent;
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
