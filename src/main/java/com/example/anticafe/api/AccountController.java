package com.example.anticafe.api;

import com.example.anticafe.service.AccountService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public final class AccountController {

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService=accountService;
    }

    private final AccountService accountService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.Account accountToDto(@NotNull com.example.anticafe.repo.model.Account accountModel){
        return  com.example.anticafe.api.dto.Account.builder()
                .username(accountModel.getUsername())
                .password_hash(accountModel.getPassword_hash())
                .first_name(accountModel.getFirst_name())
                .last_name(accountModel.getLast_name())
                .role_id(accountModel.getRole().getId())
                .email(accountModel.getEmail())
                .phone_number(accountModel.getPhone_number())
                .build();
    }

    @GetMapping
    public @NotNull ResponseEntity<List<com.example.anticafe.api.dto.Account>> index(){
            final List<com.example.anticafe.api.dto.Account> accounts = accountService.fetchAll().stream().map(this::accountToDto).collect(Collectors.toList());
            return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.Account> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.Account account = accountToDto(accountService.fetchById(id));
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@RequestBody @NotNull com.example.anticafe.api.dto.Account account){
        final String username = account.getUsername();
        final String password_hash = account.getPassword_hash();
        final String first_name = account.getFirst_name();
        final String last_name = account.getLast_name();
        final long role_id = account.getRole_id();
        final String email = account.getEmail();
        final String phone_number = account.getPhone_number();
        final long id = accountService.create(username, password_hash, first_name, last_name, role_id, email, phone_number);
        final String location = String.format("/accounts/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull com.example.anticafe.api.dto.Account account){
        final String username = account.getUsername();
        final String password_hash = account.getPassword_hash();
        final String first_name = account.getFirst_name();
        final String last_name = account.getLast_name();
        final long role_id = account.getRole_id();
        final String email = account.getEmail();
        final String phone_number = account.getPhone_number();

        try {
            accountService.update(id, username, password_hash, first_name, last_name, role_id, email, phone_number);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id){
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
