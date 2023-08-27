package com.example.reactive.services;

import com.example.reactive.dto.TestRecord;
import com.example.reactive.dto.TestRecordEntity;
import com.example.reactive.repositories.TestRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TestRecordsService {

    private final TestRecordRepository repository;
    private final ReactiveKafkaProducerTemplate<String, TestRecord> kafkaTemplate;

    @Transactional
    @SneakyThrows
    public Mono<String> save(TestRecord testRecord) {
        return repository.save(new TestRecordEntity(UUID.randomUUID().toString(), testRecord.name()))
            .doOnNext(entity -> {
                var record = new TestRecord(entity.getId(), entity.getName());
                kafkaTemplate.send("kafka-topic-with-compression", record)
                    .doOnSuccess(senderResult -> log.info("sent {} offset : {}", record, senderResult.recordMetadata().offset()))
                    .subscribe();
            })
            .map(TestRecordEntity::getId);
    }

}
