package com.ruunivstatisticsserver.common;

import java.time.LocalDate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDate createdDate; // 등록일

    @LastModifiedDate
    private LocalDate lastModifiedDate; // 수정일
}
