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
@Table(name = "roles")
@AllArgsConstructor
public final class Role {

    @Setter(AccessLevel.NONE)
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Role cannot be empty")
    @Column(nullable = false, unique = true)
    private String role_type;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<Account> accounts;

    public Role() {
    }

    public Role(String role_type) throws IllegalArgumentException{
        this.role_type = role_type;
    }
}
