package com.grupoHasten.pruebaSalahdin.service.impl.kafka;

import com.grupoHasten.pruebaSalahdin.model.dto.NaveEspacialDTO;
import com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial;
import com.grupoHasten.pruebaSalahdin.service.impl.businesslogic.NaveEspacialService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@EnableKafka
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test-kafka")
@EmbeddedKafka(partitions = 1, topics = { "nave-topic" })
public class KafkaProducerIntegrationTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private NaveEspacialService naveEspacialService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaConsumer<String, NaveEspacial> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        consumerProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        consumerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.grupoHasten.pruebaSalahdin.model.entity.NaveEspacial");
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        DefaultKafkaConsumerFactory<String, NaveEspacial> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = (KafkaConsumer<String, NaveEspacial>) consumerFactory.createConsumer();
        consumer.subscribe(Collections.singletonList("nave-topic"));
    }

    @Test
    void testSendMessage() {
        NaveEspacialDTO nave = new NaveEspacialDTO();
        nave.setId(1L);
        nave.setName("Falcon 9");

        naveEspacialService.save(nave);

        ConsumerRecord<String, NaveEspacial> singleRecord = KafkaTestUtils.getSingleRecord(consumer, "nave-topic");
        NaveEspacial receivedNave = singleRecord.value();

        System.out.println(receivedNave);
        assertThat(receivedNave).isNotNull();
        assertThat(receivedNave.getId()).isEqualTo(nave.getId());
        assertThat(receivedNave.getName()).isEqualTo(nave.getName());
    }
}
