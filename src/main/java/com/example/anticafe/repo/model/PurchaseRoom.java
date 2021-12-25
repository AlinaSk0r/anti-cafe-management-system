package com.example.anticafe.repo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "purchases_rooms")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PurchaseRoom {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Account customer_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room_id;

    @Column(nullable = false)
    private long amount_hours;

    @Column(nullable = false)
    private boolean if_fullday;

    @Column(nullable = false)
    private boolean if_night;

    private BigDecimal total;

}
