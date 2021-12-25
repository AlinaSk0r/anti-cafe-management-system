package com.example.anticafe.service;

import com.example.anticafe.repo.RoleRepo;
import com.example.anticafe.repo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class RoleService {
    private final RoleRepo roleRepo;

    public List<Role> fetchAll(){
        return roleRepo.findAll();
    }

    public Role fetchById(long id)throws IllegalArgumentException{
        final Optional<Role> maybeRole = roleRepo.findById(id);

        if (maybeRole.isEmpty()) throw new IllegalArgumentException("Role not found");
        else return maybeRole.get();
    }

    public long create(String role_type){
        final Role role = new Role(role_type);
        final Role savedRole = roleRepo.save(role);

        return savedRole.getId();
    }

    public void update(Long id, String role_type) throws IllegalArgumentException{
        final Optional<Role> maybeRole = roleRepo.findById(id);

        if (maybeRole.isEmpty()) throw new IllegalArgumentException("Role not found");
        final Role role = maybeRole.get();
        if (role_type != null && !role_type.isBlank()) role.setRole_type(role_type);

        roleRepo.save(role);
    }

    public void delete(long id)throws IllegalArgumentException{
        roleRepo.deleteById(id);
    }
}
