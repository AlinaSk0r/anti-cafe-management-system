package com.example.anticafe.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class Game {

    @NotBlank
    private String name;

    @NotBlank
    private long room_id;

    @NotBlank
    private long game_type;

    @NotBlank
    private String number_of_players;

    @NotBlank
    @Length(max = 3)
    private String age_rating_IARC;

    @NotBlank
    private String creator;

    @NotBlank
    private String genre;

    @NotBlank
    private String description;
}
