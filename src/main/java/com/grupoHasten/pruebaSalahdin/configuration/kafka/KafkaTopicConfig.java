package com.grupoHasten.pruebaSalahdin.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@ConditionalOnProperty(value = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaTopicConfig {

    @Bean
    public NewTopic naveTopic() {
        return new NewTopic("nave-topic", 1, (short) 1);
    }
}
