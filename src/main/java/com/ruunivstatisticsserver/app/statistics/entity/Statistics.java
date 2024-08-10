package com.ruunivstatisticsserver.app.statistics.entity;

import com.ruunivstatisticsserver.common.BaseTimeEntity;
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
public class Statistics extends BaseTimeEntity {

    @Id
    private String id;
    
    private Method method;

    private Api api;

    private int status;

    private String apiKey;
}
