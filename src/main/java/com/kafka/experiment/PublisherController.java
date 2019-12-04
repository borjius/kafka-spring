package com.kafka.experiment;

import com.kafka.experiment.kafka.KafkaPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublisherController {

    final static Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private final KafkaPublisher kafkaPublisher;

    @Autowired
    public PublisherController(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }


    @RequestMapping("/orders/{id_name}")
    public void publishOrders(@PathVariable("id_name") String id) {
        Order order = Order.newBuilder()
            .setOrderId(id)
            .setCustomerId("CId432")
            .setSupplierId("SId543")
            .setItems(4)
            .setFirstName("Test")
            .setLastName("V")
            .setPrice(178f)
            .setWeight(75f)
            .build();

        if (kafkaPublisher.publishOrder(order)) {
            logger.info("order " + id + " sent");
        } else {
            logger.info("order " + id + " not sent");
        }
    }


}
