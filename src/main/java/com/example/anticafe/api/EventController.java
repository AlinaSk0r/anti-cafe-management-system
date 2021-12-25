package com.example.anticafe.api;

import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Event;
import com.example.anticafe.repo.model.Room;
import com.example.anticafe.service.AccountService;
import com.example.anticafe.service.EventService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public final class EventController {

    @Autowired
    public  EventController(EventService eventService){
        this.eventService=eventService;
    }

    private final EventService eventService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.Event eventToDto(@NotNull Event eventModel){
        return  com.example.anticafe.api.dto.Event.builder()
                .room_id(eventModel.getRoom_id().getId())
                .name(eventModel.getName())
                .start_of_event(eventModel.getStart_of_event())
                .end_of_event(eventModel.getEnd_of_event())
                .date_of_event(eventModel.getDate_of_event())
                .last_day_of_event(eventModel.getLast_day_of_event())
                .description(eventModel.getDescription())
                .poster(eventModel.getPoster())
                .price(eventModel.getPrice())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<com.example.anticafe.api.dto.Event>> index(){
        final List<com.example.anticafe.api.dto.Event> events = eventService.fetchAll().stream().map(this::eventToDto).collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.Event> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.Event event = eventToDto(eventService.fetchById(id));
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.anticafe.api.dto.Event event){
        final long room_id = event.getRoom_id();
        final String name = event.getName();
        final Time start_of_event = event.getStart_of_event();
        final Time end_of_event = event.getEnd_of_event();
        final Date date_of_event = event.getDate_of_event();
        final Date last_day_of_event = event.getLast_day_of_event();
        final String description = event.getDescription();
        final String poster = event.getPoster();
        final BigDecimal price = event.getPrice();
        final long organizer_id = event.getOrganizer_id();
        final long id = eventService.create(room_id, name, start_of_event, end_of_event, date_of_event, last_day_of_event, description, poster, price, organizer_id);
        final String location = String.format("/events/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.anticafe.api.dto.Event event){
        final long room_id = event.getRoom_id();
        final String name = event.getName();
        final Time start_of_event = event.getStart_of_event();
        final Time end_of_event = event.getEnd_of_event();
        final Date date_of_event = event.getDate_of_event();
        final Date last_day_of_event = event.getLast_day_of_event();
        final String description = event.getDescription();
        final String poster = event.getPoster();
        final BigDecimal price = event.getPrice();
        final long organizer_id = event.getOrganizer_id();

        try {
            eventService.update(id, room_id, name, start_of_event, end_of_event, date_of_event, last_day_of_event, description, poster, price, organizer_id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}