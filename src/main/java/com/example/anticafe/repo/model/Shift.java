package com.example.anticafe.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "shifts")
public final class Shift {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @Column(nullable = false)
    private Time start_of_shift;

    @Column(nullable = false)
    private Time end_of_shift;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room_id;

    @Column(nullable = false)
    private BigDecimal salary;

    public Shift() {
    }

    public Shift(Time start_of_shift, Time end_of_shift, Account account_id, Room room_id, BigDecimal salary) {
        this.start_of_shift = start_of_shift;
        this.end_of_shift = end_of_shift;
        this.account_id = account_id;
        this.room_id = room_id;
        this.salary = salary;
    }
}
