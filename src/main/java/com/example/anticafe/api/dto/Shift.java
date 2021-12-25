package com.example.anticafe.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Shift {

    private Time start_of_shift;

    private Time end_of_shift;

    private long account_id;

    private long room_id;

    private BigDecimal salary;
}
