package com.project.springapistudy.common.jpa;

import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Comment("생성일")
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Comment("수정일")
    @Column(name = "UPD_DATE")
    private LocalDateTime updateDate;
}
