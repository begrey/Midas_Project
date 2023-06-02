package com.midas.midas_project.model;

/*
    공통 적용 Entity (생성, 수정 시간)
 */

import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @CreationTimestamp
    @Comment("생성 일자")
    @Column(nullable = false, name = "create_datetime")
    private LocalDateTime createDateTime;

    @LastModifiedDate
    @UpdateTimestamp
    @Comment("수정 일자")
    @Column(nullable = false, name = "modify_datetime")
    private LocalDateTime modifyDateTime;

}

