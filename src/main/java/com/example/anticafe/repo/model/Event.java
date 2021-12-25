package com.example.anticafe.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public final class Event {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idevents", nullable = false, updatable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room_id;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the name of the event")
    private String name;

    @Column(nullable = false)
    private Time start_of_event;

    @Column(nullable = false)
    private Time end_of_event;

    @Column(nullable = false)
    private Date date_of_event;

    private Date last_day_of_event;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the description")
    private String description;

    private String poster;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Account organizer_id;

    @OneToMany(mappedBy = "event_id", fetch = FetchType.LAZY)
    private List<PurchaseEvent> purchasesEvents;

    public Event() {
    }

    public Event(Room room_id, String name, Time start_of_event, Time end_of_event, Date date_of_event, Date last_day_of_event, String description, String poster, BigDecimal price, Account organizer_id) {
        this.room_id = room_id;
        this.name = name;
        this.start_of_event = start_of_event;
        this.end_of_event = end_of_event;
        this.date_of_event = date_of_event;
        this.last_day_of_event = last_day_of_event;
        this.description = description;
        this.poster = poster;
        this.price = price;
        this.organizer_id = organizer_id;
    }
}
