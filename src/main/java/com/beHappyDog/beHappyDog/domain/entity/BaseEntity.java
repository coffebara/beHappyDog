package com.beHappyDog.beHappyDog.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@EntityListeners(AutoCloseable.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdDate;

    @LastModifiedDate
    private  LocalDateTime lastModifiedDate;
}
