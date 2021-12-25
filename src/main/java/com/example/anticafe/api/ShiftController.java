package com.example.anticafe.api;

import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Room;
import com.example.anticafe.repo.model.Shift;
import com.example.anticafe.service.AccountService;
import com.example.anticafe.service.ShiftService;
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
@RequestMapping("/shifts")
public final class ShiftController {

    @Autowired
    public ShiftController(ShiftService shiftService){
        this.shiftService=shiftService;
    }

    private final ShiftService shiftService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.Shift shiftToDto(@NotNull Shift shiftModel){
        return  com.example.anticafe.api.dto.Shift.builder()
                .start_of_shift(shiftModel.getStart_of_shift())
                .end_of_shift(shiftModel.getEnd_of_shift())
                .account_id(shiftModel.getAccount_id().getId())
                .room_id(shiftModel.getRoom_id().getId())
                .salary(shiftModel.getSalary())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<com.example.anticafe.api.dto.Shift>> index(){
        final List<com.example.anticafe.api.dto.Shift> shifts = shiftService.fetchAll().stream().map(this::shiftToDto).collect(Collectors.toList());
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.Shift> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.Shift shift = shiftToDto(shiftService.fetchById(id));
            return ResponseEntity.ok(shift);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.anticafe.api.dto.Shift shift){
        final Time start_of_shift = shift.getStart_of_shift();
        final Time end_of_shift = shift.getEnd_of_shift();
        final long account_id = shift.getAccount_id();
        final long room_id = shift.getRoom_id();
        final BigDecimal salary = shift.getSalary();
        final long id = shiftService.create(start_of_shift,  end_of_shift,  account_id,  room_id,  salary);
        final String location = String.format("/shifts/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.anticafe.api.dto.Shift shift){
        final Time start_of_shift = shift.getStart_of_shift();
        final Time end_of_shift = shift.getEnd_of_shift();
        final long account_id = shift.getAccount_id();
        final long room_id = shift.getRoom_id();
        final BigDecimal salary = shift.getSalary();

        try {
            shiftService.update(id, start_of_shift,  end_of_shift,  account_id,  room_id,  salary);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        shiftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}