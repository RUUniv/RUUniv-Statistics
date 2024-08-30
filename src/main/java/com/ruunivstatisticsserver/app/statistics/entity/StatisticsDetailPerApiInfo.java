package com.ruunivstatisticsserver.app.statistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StatisticsDetailPerApiInfo {
    @Id
    private String id;
    private Api api;
    private int totalApiRequestCount;
}
