package com.example.anticafe.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public final class Room {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrooms", nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Please, write in the name of the room")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Please, write in the size")
    @Column(nullable = false)
    private String size;

    @NotBlank(message = "Please, write in the description")
    @Column(nullable = false)
    private String description;

    @NotBlank(message = "Please, write in the path of the photo")
    @Column(nullable = false)
    private String photo;

    @Column(nullable = false)
    private Time opens;

    @Column(nullable = false)
    private Time closes;

    @Column(nullable = false)
    private BigDecimal price_for_hour;

    @Column(nullable = false)
    private BigDecimal price_for_next_hour;

    @Column(nullable = false)
    private BigDecimal price_for_day;

    private BigDecimal price_for_night;

    @OneToMany(mappedBy = "room_id", fetch = FetchType.LAZY)
    private List<Game> games;

    @OneToMany(mappedBy = "room_id", fetch = FetchType.LAZY)
    private List<Shift> shifts;

    @OneToMany(mappedBy = "room_id", fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "room_id", fetch = FetchType.LAZY)
    private List<PurchaseRoom> purchasesRooms;

    public Room(){

    }

    public Room(String name, String size, String description, String photo, Time opens, Time closes, BigDecimal price_for_hour, BigDecimal price_for_next_hour, BigDecimal price_for_day, BigDecimal price_for_night) {
        this.name = name;
        this.size = size;
        this.description = description;
        this.photo = photo;
        this.opens = opens;
        this.closes = closes;
        this.price_for_hour = price_for_hour;
        this.price_for_next_hour = price_for_next_hour;
        this.price_for_day = price_for_day;
        this.price_for_night = price_for_night;
    }
}
