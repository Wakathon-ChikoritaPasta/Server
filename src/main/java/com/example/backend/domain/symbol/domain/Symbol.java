package com.example.backend.domain.symbol.domain;

import com.example.backend.domain.user.domain.User;
import com.example.backend.global.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@Getter
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Symbol extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SymbolType title;

    @Column(nullable = false)
    private String description;

    public void updateSymbolType() {
        SymbolType nextSymbolType = SymbolType.updateSymbolType(this.title);
        this.title = Objects.isNull(nextSymbolType) ? this.title : nextSymbolType;
    }
}
