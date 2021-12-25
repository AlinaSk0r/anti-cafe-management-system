package com.example.anticafe.api;

import com.example.anticafe.repo.model.Game;
import com.example.anticafe.service.GameService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public final class GameController {

    @Autowired
    public GameController(GameService gameService){
        this.gameService=gameService;
    }

    private final GameService gameService;

    @Contract("_ -> new")
    private @NotNull com.example.anticafe.api.dto.Game gameToDto(@NotNull Game gameModel){
        return  com.example.anticafe.api.dto.Game.builder()
                .name(gameModel.getName())
                .room_id(gameModel.getRoom_id().getId())
                .game_type(gameModel.getGame_type().getId())
                .number_of_players(gameModel.getNumber_of_players())
                .age_rating_IARC(gameModel.getAge_rating_IARC())
                .creator(gameModel.getCreator())
                .genre(gameModel.getGenre())
                .description(gameModel.getDescription())
                .build();
    }

    @GetMapping
    public @NotNull ResponseEntity<List<com.example.anticafe.api.dto.Game>> index(){
        final List<com.example.anticafe.api.dto.Game> games = gameService.fetchAll().stream().map(this::gameToDto).collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.anticafe.api.dto.Game> show(@PathVariable long id){
        try {
            final com.example.anticafe.api.dto.Game game = gameToDto(gameService.fetchById(id));
            return ResponseEntity.ok(game);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull ResponseEntity<Void> create(@RequestBody @NotNull com.example.anticafe.api.dto.Game game){
        final String name = game.getName();
        final long room_id = game.getRoom_id();
        final long game_type = game.getGame_type();
        final String number_of_players = game.getNumber_of_players();
        final String age_rating_IARC = game.getAge_rating_IARC();
        final String creator = game.getCreator();
        final String genre = game.getGenre();
        final String description = game.getDescription();

        final long id = gameService.create(name,room_id,game_type,number_of_players,age_rating_IARC,creator,genre,description);
        final String location = String.format("/games/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull com.example.anticafe.api.dto.Game game){
        final String name = game.getName();
        final long room_id = game.getRoom_id();
        final long game_type = game.getGame_type();
        final String number_of_players = game.getNumber_of_players();
        final String age_rating_IARC = game.getAge_rating_IARC();
        final String creator = game.getCreator();
        final String genre = game.getGenre();
        final String description = game.getDescription();

        try {
            gameService.update(id, name,room_id,game_type,number_of_players,age_rating_IARC,creator,genre,description);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull ResponseEntity<Void> delete(@PathVariable long id){
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}