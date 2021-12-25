package com.example.anticafe.api;

import com.example.anticafe.repo.model.*;
import com.example.anticafe.service.AccountService;
import com.example.anticafe.service.EventService;
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
@RequestMapping("/purchases_rooms")


public final class PurchaseRoomController {

    @Autowired
    public  PurchaseRoomController(PurchaseRoomService purchaseRoomService){
        this.purchaseRoomService=purchaseRoomService;
    }

    private final PurchaseRoomService purchaseRoomService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.PurchaseRoom purchaseRoomToDto(@NotNull PurchaseRoom purchaseRoomModel){
        return  com.example.anticafe.api.dto.PurchaseRoom.builder()
                .customer_id(purchaseRoomModel.getCustomer_id().getId())
                .room_id(purchaseRoomModel.getRoom_id().getId())
                .amount_hours(purchaseRoomModel.getAmount_hours())
                .if_fullday(purchaseRoomModel.isIf_fullday())
                .if_night(purchaseRoomModel.isIf_night())
                .total(purchaseRoomModel.getTotal())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<com.example.anticafe.api.dto.PurchaseRoom>> index() {
        final List<com.example.anticafe.api.dto.PurchaseRoom> purchasesRooms = purchaseRoomService.fetchAll().stream().map(this::purchaseRoomToDto).collect(Collectors.toList());
        return ResponseEntity.ok(purchasesRooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.PurchaseRoom> show(@PathVariable long id) {
        try {
            final com.example.anticafe.api.dto.PurchaseRoom purchaseRoom = purchaseRoomToDto(purchaseRoomService.fetchById(id));
            return ResponseEntity.ok(purchaseRoom);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.anticafe.api.dto.PurchaseRoom purchaseRoom) {
        final long customer_id = purchaseRoom.getCustomer_id();
        final long room_id = purchaseRoom.getRoom_id();
        final long amount_hours = purchaseRoom.getAmount_hours();
        final boolean if_fullday = purchaseRoom.isIf_fullday();
        final boolean if_night = purchaseRoom.isIf_night();
        final long id = purchaseRoomService.create(customer_id,  room_id,  amount_hours,  if_fullday,  if_night);
        final String location = String.format("/purchases_rooms/%d", id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.anticafe.api.dto.PurchaseRoom purchaseRoom) {
        final long customer_id = purchaseRoom.getCustomer_id();
        final long room_id = purchaseRoom.getRoom_id();
        final long amount_hours = purchaseRoom.getAmount_hours();
        final boolean if_fullday = purchaseRoom.isIf_fullday();
        final boolean if_night = purchaseRoom.isIf_night();

        try {
            purchaseRoomService.update(id, customer_id,  room_id,  amount_hours,  if_fullday,  if_night);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        purchaseRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}