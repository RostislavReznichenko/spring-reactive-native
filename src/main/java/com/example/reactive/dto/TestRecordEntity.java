package com.example.reactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRecordEntity {

    private String id;
    private String name;

}
