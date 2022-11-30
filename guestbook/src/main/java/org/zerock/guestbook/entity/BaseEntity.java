package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 테이블로 생성되지 않음
@EntityListeners(value={AuditingEntityListener.class})//
@Getter
public class BaseEntity {
    @CreatedDate//Entity의 생성시간 처리
    @Column(name="regDate",updatable=false)
    private LocalDateTime regDate;

    @LastModifiedDate//Entity가 수정되는것을 감지하여 자동처리
    @Column(name="moddate")
    private LocalDateTime modDate;
}
