package com.example.backend.domain.majorscore.domain;

import com.example.backend.global.BaseEntity;
import com.example.backend.global.enums.Major;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

public class MajorScore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Major major;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int total_score;

}
