package com.vicras.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@MappedSuperclass
//@org.hibernate.annotations.GenericGenerator(
//        name = "ID_GENERATOR",
//        strategy = "enhanced-sequence",
//
//        parameters = {
//                @org.hibernate.annotations.Parameter(
//                        name = "sequence_name",
//                        value = "JPWH_SEQUENCE"
//                ),
//                @org.hibernate.annotations.Parameter(
//                        name = "initial_value",
//                        value = "1000"
//                )
//        })
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    protected Long id;

    @Column(name = "entity_status")
    @Enumerated(EnumType.STRING)
    protected EntityStatus entityStatus = EntityStatus.ACTIVE;

    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @PrePersist
    public void setCreationDate() {
        this.createdAt =LocalDateTime.now();
    }

    @PreUpdate
    public void setChangeDate() {
        this.updatedAt = LocalDateTime.now();
    }

}

