package com.ruunivstatisticsserver.app.statistics.entity;

import java.io.Serializable;
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
public class StatisticsDetailPerApiInfo implements Serializable {
    @Id
    private String id;
    private Api api;
    private int totalApiRequestCount = 0;

    public void addCount() {
        this.totalApiRequestCount += 1;
    }
}
