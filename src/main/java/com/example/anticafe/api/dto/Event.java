package com.example.anticafe.api.dto;

import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Event {

    private long room_id;

    @NotBlank
    private String name;

    private Time start_of_event;

    private Time end_of_event;

    private Date date_of_event;

    private Date last_day_of_event;

    @NotBlank
    private String description;

    private String poster;

    private BigDecimal price;

    private long organizer_id;
}