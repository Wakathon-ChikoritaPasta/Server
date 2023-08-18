package com.example.backend.domain.major.domain;

import com.example.backend.global.BaseEntity;
import com.example.backend.global.enums.MajorType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
@Setter
@Getter
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Major extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MajorType name;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long totalExperience;

    public void updateTotalExperience(Long addExperience){
        this.totalExperience += addExperience;
    }

}
