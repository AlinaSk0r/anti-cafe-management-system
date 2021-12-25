package com.example.anticafe.service;

import com.example.anticafe.exception.AccountNotFoundException;
import com.example.anticafe.exception.RoleNotFoundException;
import com.example.anticafe.exception.RoomNotFoundException;
import com.example.anticafe.repo.AccountRepo;
import com.example.anticafe.repo.RoomRepo;
import com.example.anticafe.repo.ShiftRepo;
import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Room;
import com.example.anticafe.repo.model.Shift;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class ShiftService {

    private final ShiftRepo shiftRepo;
    private final AccountRepo accountRepo;
    private final RoomRepo roomRepo;

    public List<Shift> fetchAll(){
        return shiftRepo.findAll();
    }

    public Shift fetchById(long id)throws IllegalArgumentException{
        final Optional<Shift> maybeShift = shiftRepo.findById(id);

        if (maybeShift.isEmpty()) throw new IllegalArgumentException("Shift not found");
        else return maybeShift.get();
    }

    public long create(Time start_of_shift, Time end_of_shift, long account_id, long room_id, BigDecimal salary){
        Account account = accountRepo.findById(account_id).orElseThrow(AccountNotFoundException::new);
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        final Shift shift = new Shift( start_of_shift,  end_of_shift,  account,  room,  salary);
        final Shift savedShift = shiftRepo.save(shift);

        return savedShift.getId();
    }

    public void update(Long id, Time start_of_shift, Time end_of_shift, long account_id, long room_id, BigDecimal salary) throws IllegalArgumentException{
        final Optional<Shift> maybeShift = shiftRepo.findById(id);
        Account account = accountRepo.findById(account_id).orElseThrow(AccountNotFoundException::new);
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        if (maybeShift.isEmpty()) throw new IllegalArgumentException("Shift not found");
        final Shift shift = maybeShift.get();
        if (start_of_shift != null) shift.setStart_of_shift(start_of_shift);
        if (end_of_shift != null) shift.setEnd_of_shift(end_of_shift);
        if (account != null) shift.setAccount_id(account);
        if (room != null) shift.setRoom_id(room);
        if (salary != null) shift.setSalary(salary);

        shiftRepo.save(shift);
    }

    public void delete(long id)throws IllegalArgumentException{
        shiftRepo.deleteById(id);
    }
}
