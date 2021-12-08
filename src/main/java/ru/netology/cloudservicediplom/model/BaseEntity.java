package ru.netology.cloudservicediplom.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column
    private Date created;

    @LastModifiedDate
    @Column
    private Date updated;

    @Enumerated(EnumType.STRING)
    @Column
    private UserCondition userCondition;
}
