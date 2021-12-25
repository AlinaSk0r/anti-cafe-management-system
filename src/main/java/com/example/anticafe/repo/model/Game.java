package com.example.anticafe.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "games")
public final class Game {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "game_type", nullable = false)
    private GameType game_type;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the number of players")
    private String number_of_players;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the age rate")
    private String age_rating_IARC;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the creator")
    private String creator;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the genre")
    private String genre;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the description")
    private String description;

    public Game() {
    }

    public Game(String name,Room room_id, GameType game_type, String number_of_players, String age_rating_IARC, String creator, String genre, String description) {
        this.name = name;
        this.room_id = room_id;
        this.game_type = game_type;
        this.number_of_players = number_of_players;
        this.age_rating_IARC = age_rating_IARC;
        this.creator = creator;
        this.genre = genre;
        this.description = description;
    }
}
