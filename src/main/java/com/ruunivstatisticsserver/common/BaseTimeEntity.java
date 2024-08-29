package com.ruunivstatisticsserver.common;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate; // 등록일

    @LastModifiedDate
    private LocalDateTime lastModifiedDate; // 수정일
}
