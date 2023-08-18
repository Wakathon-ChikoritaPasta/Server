package com.example.backend.domain.level.domain;

import com.example.backend.domain.ladybug.domain.LadybugType;
import com.example.backend.domain.user.domain.User;
import com.example.backend.global.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Level extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private Long experience;

    @Column(nullable = false)
    private LadybugType ladybugType;

    public void updateExperience(int experience){
        this.experience += experience;
    }

    public void updateLevel(){
        int addLevel = (int) (this.experience / 1000);
        this.level += addLevel;
    }

    public void updateLadybugType(){
        LadybugType nextLadybugType = LadybugType.updateLadybugType(this.ladybugType);
        this.ladybugType = Objects.isNull(nextLadybugType) ? this.ladybugType : nextLadybugType;
    }
}
