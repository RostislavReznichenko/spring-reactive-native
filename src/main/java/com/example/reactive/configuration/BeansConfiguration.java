package com.example.reactive.configuration;

import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagationConfig.SingleBaggageField;
import brave.baggage.BaggagePropagationCustomizer;
import brave.baggage.CorrelationScopeConfig.SingleCorrelationField;
import brave.baggage.CorrelationScopeCustomizer;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class BeansConfiguration {

    @Bean
    BaggagePropagationCustomizer baggagePropagationCustomizer() {
        return builder -> {
            builder.add(SingleBaggageField.remote(BaggageField.create("X-CORRELATION-ID")));
            builder.add(SingleBaggageField.local(BaggageField.create("X-CORRELATION-ID")));
        };
    }

    @Bean
    CorrelationScopeCustomizer correlationScopeCustomizer() {
        return builder -> builder.add(SingleCorrelationField.create(BaggageField.create("X-CORRELATION-ID")));
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

}
