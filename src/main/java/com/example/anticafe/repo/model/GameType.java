package com.example.anticafe.repo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "game_types")
@AllArgsConstructor
public final class GameType {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Game type cannot be empty")
    private String game_type;

    @OneToMany(mappedBy = "game_type", fetch = FetchType.LAZY)
    private List<Game> games;

    public GameType() {
    }

    public GameType(String game_type) throws IllegalArgumentException{
        this.game_type = game_type;
    }
}
