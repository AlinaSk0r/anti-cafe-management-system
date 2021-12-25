package com.example.anticafe.api;

import com.example.anticafe.repo.model.Room;
import com.example.anticafe.service.RoomService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public final class RoomController {

    @Autowired
    public RoomController(RoomService roomService){
        this.roomService=roomService;
    }

    private final RoomService roomService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.Room roomToDto(@NotNull Room roomModel){
        return  com.example.anticafe.api.dto.Room.builder()
                .name(roomModel.getName())
                .size(roomModel.getSize())
                .description(roomModel.getDescription())
                .photo(roomModel.getPhoto())
                .opens(roomModel.getOpens())
                .closes(roomModel.getCloses())
                .price_for_hour(roomModel.getPrice_for_hour())
                .price_for_next_hour(roomModel.getPrice_for_next_hour())
                .price_for_day(roomModel.getPrice_for_day())
                .price_for_night(roomModel.getPrice_for_night())
                .build();
    }

    @GetMapping
    public @NotNull ResponseEntity<List<com.example.anticafe.api.dto.Room>> index(){
        final List<com.example.anticafe.api.dto.Room> rooms = roomService.fetchAll().stream().map(this::roomToDto).collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.Room> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.Room room = roomToDto(roomService.fetchById(id));
            return ResponseEntity.ok(room);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@RequestBody @NotNull com.example.anticafe.api.dto.Room room){
        final String name = room.getName();
        final String size = room.getSize();
        final String description = room.getDescription();
        final String photo = room.getPhoto();
        final Time opens = room.getOpens();
        final Time closes = room.getCloses();
        final BigDecimal price_for_hour = room.getPrice_for_hour();
        final BigDecimal price_for_next_hour = room.getPrice_for_next_hour() ;
        final BigDecimal price_for_day = room.getPrice_for_day();
        final BigDecimal price_for_night = room.getPrice_for_night();
        final long id = roomService.create(name, size, description, photo, opens, closes, price_for_hour, price_for_next_hour, price_for_day, price_for_night);
        final String location = String.format("/rooms/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull com.example.anticafe.api.dto.Room room){
        final String name = room.getName();
        final String size = room.getSize();
        final String description = room.getDescription();
        final String photo = room.getPhoto();
        final Time opens = room.getOpens();
        final Time closes = room.getCloses();
        final BigDecimal price_for_hour = room.getPrice_for_hour();
        final BigDecimal price_for_next_hour = room.getPrice_for_next_hour() ;
        final BigDecimal price_for_day = room.getPrice_for_day();
        final BigDecimal price_for_night = room.getPrice_for_night();

        try {
            roomService.update(id, name, size, description, photo, opens, closes, price_for_hour, price_for_next_hour, price_for_day, price_for_night);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id){

        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}