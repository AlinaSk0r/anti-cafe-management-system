package com.example.anticafe.api.dto;

import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class PurchaseRoom {

    private long customer_id;

    private long room_id;

    private long amount_hours;

    private boolean if_fullday;

    private boolean if_night;

    private BigDecimal total;
}
