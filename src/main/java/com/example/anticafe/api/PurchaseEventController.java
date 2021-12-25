package com.example.anticafe.api;

import com.example.anticafe.repo.model.*;
import com.example.anticafe.service.AccountService;
import com.example.anticafe.service.PurchaseEventService;
import com.example.anticafe.service.PurchaseRoomService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases_events")


public final class PurchaseEventController {

    @Autowired
    public  PurchaseEventController(PurchaseEventService purchaseEventService){
        this.purchaseEventService=purchaseEventService;
    }

    private final PurchaseEventService purchaseEventService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.PurchaseEvent purchaseEventToDto(@NotNull PurchaseEvent purchaseEventModel){
        return  com.example.anticafe.api.dto.PurchaseEvent.builder()
                .customer_id(purchaseEventModel.getCustomer_id().getId())
                .event_id(purchaseEventModel.getEvent_id().getId())
                .amount_tickets(purchaseEventModel.getAmount_tickets())
                .total(purchaseEventModel.getTotal())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<com.example.anticafe.api.dto.PurchaseEvent>> index(){
        final List<com.example.anticafe.api.dto.PurchaseEvent> purchasesEvents = purchaseEventService.fetchAll().stream().map(this::purchaseEventToDto).collect(Collectors.toList());
        return ResponseEntity.ok(purchasesEvents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.PurchaseEvent> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.PurchaseEvent purchaseEvent = purchaseEventToDto(purchaseEventService.fetchById(id));
            return ResponseEntity.ok(purchaseEvent);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.anticafe.api.dto.PurchaseEvent purchaseEvent){
        final long customer_id = purchaseEvent.getCustomer_id();
        final long event_id = purchaseEvent.getEvent_id();
        final long amount_tickets = purchaseEvent.getAmount_tickets();
        final long id = purchaseEventService.create(customer_id,  event_id,  amount_tickets);
        final String location = String.format("/purchases_events/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.anticafe.api.dto.PurchaseEvent purchaseEvent){
        final long customer_id = purchaseEvent.getCustomer_id();
        final long event_id = purchaseEvent.getEvent_id();
        final long amount_tickets = purchaseEvent.getAmount_tickets();
        try {
            purchaseEventService.update(id, customer_id,  event_id,  amount_tickets);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        purchaseEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}