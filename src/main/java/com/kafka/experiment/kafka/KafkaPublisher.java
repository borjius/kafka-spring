package com.kafka.experiment.kafka;

import com.kafka.experiment.Order;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    private final static Logger logger = LoggerFactory.getLogger(KafkaPublisher.class);

    @Value("${topic.boot}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public boolean publishOrder(Order order) {
        ProducerRecord<String, Order> producerRecord = new ProducerRecord<>(topicName, order);

        logger.info("Publishing order id {}", order.getOrderId());

        kafkaTemplate.send(producerRecord);

        return true;
    }

}
