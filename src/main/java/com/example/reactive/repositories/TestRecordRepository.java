package com.example.reactive.repositories;

import com.example.reactive.dto.TestRecordEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TestRecordRepository extends ReactiveCrudRepository<TestRecordEntity, String> {
}
