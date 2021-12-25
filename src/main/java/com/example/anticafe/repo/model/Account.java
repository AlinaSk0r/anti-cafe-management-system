package com.example.anticafe.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public final class Account {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Username cannot be empty")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Column(name = "password_hash", nullable = false)
    private String password_hash;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the first name")
    private String first_name;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the last name")
    private String last_name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the email")
    private String email;

    private String phone_number;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private final Timestamp date_registr = new Timestamp(System.currentTimeMillis());

    @OneToMany(mappedBy = "account_id", fetch = FetchType.LAZY)
    private List<Shift> shifts;

    @OneToMany(mappedBy = "organizer_id", fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "customer_id", fetch = FetchType.LAZY)
    private List<PurchaseRoom> purchasesRooms;

    @OneToMany(mappedBy = "customer_id", fetch = FetchType.LAZY)
    private List<PurchaseEvent> purchasesEvents;

    public Account() {
    }

    public Account(String username, String password_hash, String first_name, String last_name, Role role, String email, String phone_number) throws IllegalArgumentException{
        this.username = username;
        this.password_hash = password_hash;
        this.first_name = first_name;
        this.last_name = last_name;
        this.role = role;
        this.email = email;
        this.phone_number = phone_number;
    }

}

