package com.kafka.experiment.kafka;

import com.kafka.experiment.Order;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.event.NonResponsiveConsumerEvent;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final String ORDERS_TOPIC = "${topic.boot}";

    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = {ORDERS_TOPIC})
    public void receive(ConsumerRecord<String, Order> consumerRecord) {
        String key = consumerRecord.key();
        Order order = consumerRecord.value();
        if (order != null) {
            logger.info("Order read " + order.getOrderId() + " key:::: " + key );
        }


        latch.countDown();
    }

    @EventListener()
    public void eventHandler(NonResponsiveConsumerEvent event) {
        logger.error("Kafka is down, consumer not receiving events {}", event);
    }

}
