package com.example.anticafe.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class Account {

    @NotBlank
    private String username;

    @NotBlank
    private String password_hash;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    private long role_id;

    @NotBlank
    private String email;

    private String phone_number;
}
