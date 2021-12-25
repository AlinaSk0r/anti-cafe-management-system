package com.example.anticafe.service;

import com.example.anticafe.exception.RoleNotFoundException;
import com.example.anticafe.repo.AccountRepo;
import com.example.anticafe.repo.RoleRepo;
import com.example.anticafe.repo.model.Account;
import com.example.anticafe.repo.model.Role;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class AccountService {

    private final AccountRepo accountRepo;
    private final RoleRepo roleRepo;

    public @NotNull List<Account> fetchAll(){
        return accountRepo.findAll();
    }

    public @NotNull Account fetchById(long id)throws IllegalArgumentException{
        final Optional<Account> maybeAccount = accountRepo.findById(id);

        if (maybeAccount.isEmpty()) throw new IllegalArgumentException("Account not found");
        else return maybeAccount.get();
    }

    public long create(String username, String password_hash, String first_name, String last_name, long role_id, String email, String phone_number){
        Role role = roleRepo.findById(role_id).orElseThrow(RoleNotFoundException::new);
        final Account account = new Account(username,password_hash,first_name,last_name,role,email,phone_number);
        final Account savedAccount = accountRepo.save(account);

        return savedAccount.getId();
    }

    public void update(Long id, String username, String password_hash, String first_name, String last_name, long role_id, String email, String phone_number) throws IllegalArgumentException{
        final Optional<Account> maybeAccount = accountRepo.findById(id);
        Role role = roleRepo.findById(role_id).orElseThrow(RoleNotFoundException::new);
        if (maybeAccount.isEmpty()) throw new IllegalArgumentException("Account not found");
        final Account account = maybeAccount.get();
        if (username != null && !username.isBlank()) account.setUsername(username);
        if (password_hash != null && !password_hash.isBlank()) account.setPassword_hash(password_hash);
        if (first_name != null && !first_name.isBlank()) account.setFirst_name(first_name);
        if (last_name != null && !last_name.isBlank()) account.setLast_name(last_name);
        if (role!= null) account.setRole(role);
        if (email != null && !email.isBlank()) account.setEmail(email);
        account.setPhone_number(phone_number);

        accountRepo.save(account);
    }

    public void delete(long id)throws IllegalArgumentException{
        accountRepo.deleteById(id);
    }
}
