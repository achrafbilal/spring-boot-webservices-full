package com.bilalachraf.dataanalyticsservice.web;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.bilalachraf.dataanalyticsservice.events.BillEvent;
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

@RestController
public class AnalyticsRestController {

	@Autowired
	private InteractiveQueryService interactiveQueryService;
	private void print(String message) {
		System.out.println("MESSAGE : "+message);
	}

	@GetMapping(value = "/analytics",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Map<Long,Long>> analytics(){
		return Flux.interval(Duration.ofSeconds(1))
				.map(sequence->{
					Map<Long,Long> stringLongMap=new HashMap<>();
					ReadOnlyWindowStore<Long,Long> windowStore=interactiveQueryService.getQueryableStore("bill-count", QueryableStoreTypes.windowStore());
					Instant now=Instant.now();
					Instant from=now.minus(1, ChronoUnit.DAYS);
					KeyValueIterator<Windowed<Long>,Long> fetchAll=windowStore.fetchAll(from,now);
					while ((fetchAll.hasNext())){
						KeyValue<Windowed<Long>,Long> next=fetchAll.next();
						//System.out.println(next);
						print(next.key.key()+" => "+next.value);
						stringLongMap.put(next.key.key(),next.value);
					}
					return stringLongMap;
				}).share();
	}
}
