package com.bilalachraf.billingsupplierservice.web;

import com.bilalachraf.billingsupplierservice.events.BillEvent;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PageEventRestController {
	@Autowired 
	private StreamBridge streamBridge;

	@Autowired
	private InteractiveQueryService interactiveQueryService;
	
	@GetMapping("/publish/{topic}/{message}")
	public BillEvent publish(@PathVariable String topic, @PathVariable String message) {
		BillEvent billEvent=new BillEvent();
		streamBridge.send(topic, billEvent);
		return billEvent;
	}

	@GetMapping(value = "/analytics"/*{page}*/,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Map<String,Long>> analytics(/*@PathVariable String page*/){
		return Flux.interval(Duration.ofSeconds(1))
				.map(sequence->{
					Map<String,Long> stringLongMap=new HashMap<>();
					ReadOnlyWindowStore<String,Long> windowStore=interactiveQueryService.getQueryableStore("page-count", QueryableStoreTypes.windowStore());
					Instant now=Instant.now();
					Instant from=now.minusMillis(2000);//TODO 5000
					KeyValueIterator<Windowed<String>,Long> fetchAll=windowStore.fetchAll(from,now);
					while ((fetchAll.hasNext())){
						KeyValue<Windowed<String>,Long> next=fetchAll.next();
						stringLongMap.put(next.key.key(),next.value);
						//System.out.printf("Key : %s ; Value : %d%n",next.key.key(),next.value);
					}
//					WindowStoreIterator<Long> fetchAll=windowStore.fetch(page,from,now);
//					while ((fetchAll.hasNext())){
//						KeyValue<Long,Long> next=fetchAll.next();
//						stringLongMap.put(page,next.value);
//						System.out.printf("Key : %s ; Value : %d%n",page,next.value);
//					}
					return stringLongMap;
				}).share();
	}
}
