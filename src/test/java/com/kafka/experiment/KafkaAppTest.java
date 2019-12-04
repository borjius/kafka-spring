package com.kafka.experiment;


import static org.assertj.core.api.Java6Assertions.assertThat;

import com.kafka.experiment.kafka.KafkaConsumer;
import com.kafka.experiment.kafka.KafkaPublisher;
import java.util.concurrent.TimeUnit;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaAppTest {

    private static final String BOOT_TOPIC = "boot.t";

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, false, 1, BOOT_TOPIC);

    @Autowired
    private KafkaPublisher sender;

    @Autowired
    private KafkaConsumer receiver;

    @BeforeClass
    public static void setUp() {

        System.setProperty("kafka.bootstrap-servers", embeddedKafka.getEmbeddedKafka().getBrokersAsString());

    }

    @Test
    public void testReceive() throws Exception {
        Order order = Order.newBuilder()
            .setOrderId("rgrggrg")
            .setCustomerId("CId432")
            .setSupplierId("SId543")
            .setItems(4)
            .setFirstName("Test")
            .setLastName("V")
            .setPrice(178f)
            .setWeight(75f)
            .build();
        sender.publishOrder(order);

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }

}
