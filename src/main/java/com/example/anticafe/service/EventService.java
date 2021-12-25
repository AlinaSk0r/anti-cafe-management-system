package com.example.anticafe.service;

import com.example.anticafe.exception.AccountNotFoundException;
import com.example.anticafe.exception.RoleNotFoundException;
import com.example.anticafe.exception.RoomNotFoundException;
import com.example.anticafe.repo.AccountRepo;
import com.example.anticafe.repo.EventRepo;
import com.example.anticafe.repo.RoomRepo;
import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Event;
import com.example.anticafe.repo.model.Role;
import com.example.anticafe.repo.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class EventService {

    private final RoomRepo roomRepo;
    private final AccountRepo accountRepo;
    private final EventRepo eventRepo;

    public List<Event> fetchAll(){
        return eventRepo.findAll();
    }

    public Event fetchById(long id)throws IllegalArgumentException{
        final Optional<Event> maybeEvent = eventRepo.findById(id);

        if (maybeEvent.isEmpty()) throw new IllegalArgumentException("Event not found");
        else return maybeEvent.get();
    }

    public long create(long room_id, String name, Time start_of_event, Time end_of_event, Date date_of_event, Date last_day_of_event, String description, String poster, BigDecimal price, long organizer_id){
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        Account account = accountRepo.findById(organizer_id).orElseThrow(AccountNotFoundException::new);
        final Event event = new Event( room, name, start_of_event, end_of_event, date_of_event, last_day_of_event, description, poster, price, account);
        final Event savedEvent = eventRepo.save(event);

        return savedEvent.getId();
    }

    public void update(Long id, long room_id, String name, Time start_of_event, Time end_of_event, Date date_of_event, Date last_day_of_event, String description, String poster, BigDecimal price, long organizer_id) throws IllegalArgumentException{
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        Account account = accountRepo.findById(organizer_id).orElseThrow(AccountNotFoundException::new);
        final Optional<Event> maybeEvent = eventRepo.findById(id);

        if (maybeEvent.isEmpty()) throw new IllegalArgumentException("Event not found");
        final Event event = maybeEvent.get();
        if (room != null) event.setRoom_id(room);
        if (name != null && !name.isBlank()) event.setName(name);
        if (start_of_event != null) event.setStart_of_event(start_of_event);
        if (end_of_event != null) event.setEnd_of_event(end_of_event);
        if (date_of_event != null) event.setDate_of_event(date_of_event);
        if (last_day_of_event != null) event.setLast_day_of_event(last_day_of_event);
        if (description != null && !description.isBlank()) event.setDescription(description);
        event.setPoster(poster);
        if (price != null) event.setPrice(price);
        if (account != null) event.setOrganizer_id(account);

        eventRepo.save(event);
    }

    public void delete(long id)throws IllegalArgumentException{
        eventRepo.deleteById(id);
    }
}