package com.example.reactive.configuration;

import com.example.reactive.dto.TestRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class ReactiveKafkaConsumerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, TestRecord> reactiveKafkaProducerTemplate(
        KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, TestRecord>(SenderOptions.create(props));
    }

}
