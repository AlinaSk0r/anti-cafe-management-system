package com.example.anticafe.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Time;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class Room {

    @NotBlank
    private String name;

    @NotBlank
    private String size;

    @NotBlank
    private String description;

    @NotBlank
    private String photo;

    private Time opens;

    private Time closes;

    private BigDecimal price_for_hour;

    private BigDecimal price_for_next_hour;

    private BigDecimal price_for_day;

    private BigDecimal price_for_night;
}
