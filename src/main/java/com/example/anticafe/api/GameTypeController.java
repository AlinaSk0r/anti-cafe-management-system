package com.example.anticafe.api;

import com.example.anticafe.repo.model.GameType;
import com.example.anticafe.service.GameTypeService;
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
@RequestMapping("/game_types")
public final class GameTypeController {

    private final GameTypeService gameTypeService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.GameType gameTypeToDto(@NotNull GameType gameTypeModel){
        return new com.example.anticafe.api.dto.GameType(gameTypeModel.getGame_type());
    }

    @GetMapping
    public @NotNull ResponseEntity<List<com.example.anticafe.api.dto.GameType>> index(){

        final List<com.example.anticafe.api.dto.GameType> gameTypes = gameTypeService.fetchAll().stream().map(this::gameTypeToDto).collect(Collectors.toList());
        return ResponseEntity.ok(gameTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.GameType> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.GameType gameType = gameTypeToDto(gameTypeService.fetchById(id));
            return ResponseEntity.ok(gameType);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@RequestBody @NotNull com.example.anticafe.api.dto.GameType gameType){
        final String game_type = gameType.getGame_type();
        final long id = gameTypeService.create(game_type);
        final String location = String.format("/game_types/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull com.example.anticafe.api.dto.GameType gameType){
        final String game_type = gameType.getGame_type();

        try {
            gameTypeService.update(id, game_type);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id){

        gameTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
