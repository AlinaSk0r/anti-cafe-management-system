package com.example.anticafe.api.dto;

import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Event;
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
public final class PurchaseEvent {

    private long customer_id;

    private long event_id;

    private long amount_tickets;

    private BigDecimal total;

}
