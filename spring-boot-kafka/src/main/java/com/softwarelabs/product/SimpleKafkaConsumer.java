package com.softwarelabs.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@Slf4j
public class SimpleKafkaConsumer<T> {

	private Consumer<String, String> consumer;
	private ObjectMapper mapper;
	private EventConsumer<T> eventConsumer;

	public SimpleKafkaConsumer(String topicName, Consumer consumer, EventConsumer<T> eventConsumer) {
		log.info("Starting Kafka consumer");
		mapper = new ObjectMapper();
		this.consumer = consumer;
		this.consumer.subscribe(Collections.singletonList(topicName));
		this.eventConsumer = eventConsumer;

	}

	public void readValue() {
		log.info("Polling from broker");
		ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.of(1000, ChronoUnit.MILLIS));
		//print each record.
		consumerRecords.forEach(record -> {
			log.info("Record Key " + record.key());
			log.info("Record value " + record.value());
			log.info("Record partition " + record.partition());
			log.info("Record offset " + record.offset());
			// commits the offset of record to broker.
			T value = null;
			try {
				value = (T) mapper.readValue(record.value(), eventConsumer.eventType());
			} catch (IOException e) {
				e.printStackTrace();
			}
			eventConsumer.consume(value);
		});
		consumer.commitAsync();
	}
}
