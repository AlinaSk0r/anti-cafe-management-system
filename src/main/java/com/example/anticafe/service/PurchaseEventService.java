package com.example.anticafe.service;

import com.example.anticafe.exception.AccountNotFoundException;
import com.example.anticafe.exception.EventNotFoundException;
import com.example.anticafe.exception.RoomNotFoundException;
import com.example.anticafe.repo.AccountRepo;
import com.example.anticafe.repo.EventRepo;
import com.example.anticafe.repo.PurchaseEventRepo;
import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Event;
import com.example.anticafe.repo.model.PurchaseEvent;
import com.example.anticafe.repo.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class PurchaseEventService {

    private final PurchaseEventRepo purchaseEventRepo;
    private final AccountRepo accountRepo;
    private final EventRepo eventRepo;


    public List<PurchaseEvent> fetchAll(){
        return purchaseEventRepo.findAll();
    }

    public PurchaseEvent fetchById(long id)throws IllegalArgumentException{
        final Optional<PurchaseEvent> maybePurchaseEvent = purchaseEventRepo.findById(id);

        if (maybePurchaseEvent.isEmpty()) throw new IllegalArgumentException("Purchase Event not found");
        else return maybePurchaseEvent.get();
    }

    public long create(long customer_id, long event_id, long amount_tickets){
        Event event = eventRepo.findById(event_id).orElseThrow(EventNotFoundException::new);
        Account account = accountRepo.findById(customer_id).orElseThrow(AccountNotFoundException::new);
        BigDecimal total;
        if (amount_tickets>0){
            total = event.getPrice().multiply(BigDecimal.valueOf(amount_tickets));
        }
        else{total=BigDecimal.valueOf(0);}
        final PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .customer_id(account)
                .event_id(event)
                .amount_tickets(amount_tickets)
                .total(total)
                .build();
        final PurchaseEvent savedPurchaseEvent = purchaseEventRepo.save(purchaseEvent);

        return savedPurchaseEvent.getId();
    }

    public void update(Long id, long customer_id, long event_id, long amount_tickets) throws IllegalArgumentException{
        final Optional<PurchaseEvent> maybePurchaseEvent = purchaseEventRepo.findById(id);
        Event event = eventRepo.findById(event_id).orElseThrow(EventNotFoundException::new);
        Account account = accountRepo.findById(customer_id).orElseThrow(AccountNotFoundException::new);
        BigDecimal total;
        if (amount_tickets>0){
            total = event.getPrice().multiply(BigDecimal.valueOf(amount_tickets));
        }
        else{amount_tickets=0; total=BigDecimal.valueOf(0);}
        if (maybePurchaseEvent.isEmpty()) throw new IllegalArgumentException("PurchaseEvent not found");
        final PurchaseEvent purchaseEvent = maybePurchaseEvent.get();
        if (account != null) purchaseEvent.setCustomer_id(account);
        if (event != null) purchaseEvent.setEvent_id(event);
        purchaseEvent.setAmount_tickets(amount_tickets);
        purchaseEvent.setTotal(total);

        purchaseEventRepo.save(purchaseEvent);
    }

    public void delete(long id)throws IllegalArgumentException{
        purchaseEventRepo.deleteById(id);
    }
}
