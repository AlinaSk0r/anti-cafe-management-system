package com.example.anticafe.service;

import com.example.anticafe.exception.AccountNotFoundException;
import com.example.anticafe.exception.RoomNotFoundException;
import com.example.anticafe.repo.AccountRepo;
import com.example.anticafe.repo.PurchaseRoomRepo;
import com.example.anticafe.repo.RoomRepo;
import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.PurchaseRoom;
import com.example.anticafe.repo.model.Role;
import com.example.anticafe.repo.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class PurchaseRoomService {

    private final PurchaseRoomRepo purchaseRoomRepo;
    private final AccountRepo accountRepo;
    private final RoomRepo roomRepo;

    public List<PurchaseRoom> fetchAll(){
        return purchaseRoomRepo.findAll();
    }

    public PurchaseRoom fetchById(long id)throws IllegalArgumentException{
        final Optional<PurchaseRoom> maybePurchaseRoom = purchaseRoomRepo.findById(id);

        if (maybePurchaseRoom.isEmpty()) throw new IllegalArgumentException("Purchase Room not found");
        else return maybePurchaseRoom.get();
    }

    public long create(long customer_id, long room_id, long amount_hours, boolean if_fullday, boolean if_night){
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        Account account = accountRepo.findById(customer_id).orElseThrow(AccountNotFoundException::new);
        if (room.getPrice_for_night() == null) if_night=false;
        BigDecimal total;
        if(amount_hours > 0) {
            total = if_fullday
                    ? (if_night ? room.getPrice_for_day().add(room.getPrice_for_night()) : room.getPrice_for_day())
                    : (if_night ? room.getPrice_for_night() : room.getPrice_for_hour().add(room.getPrice_for_next_hour().multiply(BigDecimal.valueOf(amount_hours - 1))));
        }
        else{total=BigDecimal.valueOf(0);}
        final PurchaseRoom purchaseRoom = PurchaseRoom.builder()
                .customer_id(account)
                .room_id(room)
                .amount_hours(amount_hours)
                .if_fullday(if_fullday)
                .if_night(if_night)
                .total(total).build();
        final PurchaseRoom savedPurchaseRoom = purchaseRoomRepo.save(purchaseRoom);

        return savedPurchaseRoom.getId();
    }

    public void update(Long id, long customer_id, long room_id, long amount_hours, Boolean if_fullday, Boolean if_night) throws IllegalArgumentException{
        final Optional<PurchaseRoom> maybePurchaseRoom = purchaseRoomRepo.findById(id);
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        Account account = accountRepo.findById(customer_id).orElseThrow(AccountNotFoundException::new);
        BigDecimal total;
        if (maybePurchaseRoom.isEmpty()) throw new IllegalArgumentException("PurchaseRoom not found");
        final PurchaseRoom purchaseRoom = maybePurchaseRoom.get();
        purchaseRoom.setCustomer_id(account);
        purchaseRoom.setRoom_id(room);
        purchaseRoom.setAmount_hours(amount_hours);
        purchaseRoom.setIf_fullday(if_fullday);
        if (room.getPrice_for_night() == null) purchaseRoom.setIf_night(if_night=false);
        if(amount_hours > 0) {
            total = if_fullday
                    ? (if_night ? room.getPrice_for_day().add(room.getPrice_for_night()) : room.getPrice_for_day())
                    : (if_night ? room.getPrice_for_night() : room.getPrice_for_hour().add(room.getPrice_for_next_hour().multiply(BigDecimal.valueOf(amount_hours - 1))));
        }
        else{total=BigDecimal.valueOf(0);}
        if (total != null) purchaseRoom.setTotal(total);

        purchaseRoomRepo.save(purchaseRoom);
    }

    public void delete(long id)throws IllegalArgumentException{
        purchaseRoomRepo.deleteById(id);
    }
}
