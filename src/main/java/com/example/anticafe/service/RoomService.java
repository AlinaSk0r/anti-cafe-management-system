package com.example.anticafe.service;

import com.example.anticafe.repo.RoomRepo;
import com.example.anticafe.repo.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class RoomService {

    private final RoomRepo roomRepo;

    public List<Room> fetchAll(){
        return roomRepo.findAll();
    }

    public Room fetchById(long id)throws IllegalArgumentException{
        final Optional<Room> maybeRoom = roomRepo.findById(id);

        if (maybeRoom.isEmpty()) throw new IllegalArgumentException("Room not found");
        else return maybeRoom.get();
    }

    public long create(String name, String size, String description, String photo, Time opens, Time closes, BigDecimal price_for_hour, BigDecimal price_for_next_hour, BigDecimal price_for_day, BigDecimal price_for_night){
        final Room room = new Room(name, size, description, photo, opens, closes, price_for_hour, price_for_next_hour, price_for_day, price_for_night);
        final Room savedRoom = roomRepo.save(room);

        return savedRoom.getId();
    }

    public void update(Long id, String name, String size, String description, String photo, Time opens, Time closes, BigDecimal price_for_hour, BigDecimal price_for_next_hour, BigDecimal price_for_day, BigDecimal price_for_night) throws IllegalArgumentException{
        final Optional<Room> maybeRoom = roomRepo.findById(id);

        if (maybeRoom.isEmpty()) throw new IllegalArgumentException("Room not found");
        final Room room = maybeRoom.get();
        if (name != null && !name.isBlank()) room.setName(name);
        if (size != null && !size.isBlank()) room.setSize(size);
        if (description != null && !description.isBlank()) room.setDescription(description);
        if (photo != null && !photo.isBlank()) room.setPhoto(photo);
        if (opens != null) room.setOpens(opens);
        if (closes != null) room.setCloses(closes);
        if (price_for_hour != null) room.setPrice_for_hour(price_for_hour);
        if (price_for_next_hour != null) room.setPrice_for_next_hour(price_for_next_hour);
        if (price_for_day != null) room.setPrice_for_day(price_for_day);
        room.setPrice_for_night(price_for_night);
        roomRepo.save(room);
    }

    public void delete(long id)throws IllegalArgumentException{
        roomRepo.deleteById(id);
    }
}
