package com.example.anticafe.repo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "purchases_events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PurchaseEvent {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Account customer_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event_id;

    @Column(nullable = false)
    private long amount_tickets;

    private BigDecimal total;

}
