package com.example.anticafe.api;

import com.example.anticafe.api.dto.Role;
import com.example.anticafe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public final class RoleController {

    private final RoleService roleService;

    @Contract("_ -> new")
    private @NotNull Role roleToDto(@NotNull com.example.anticafe.repo.model.Role roleModel){
        return new Role(roleModel.getRole_type());
    }

    @GetMapping
    public @NotNull ResponseEntity<List<Role>> index(){
        final List<Role> roles = roleService.fetchAll().stream().map(this::roleToDto).collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.Role> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.Role role = roleToDto(roleService.fetchById(id));
            return ResponseEntity.ok(role);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody com.example.anticafe.api.dto.Role role){

        final String role_type = role.getRole_type();
        final long id = roleService.create(role_type);
        final String location = String.format("/roles/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody com.example.anticafe.api.dto.Role role){
        final String role_type = role.getRole_type();

        try {
            roleService.update(id, role_type);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){

        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
