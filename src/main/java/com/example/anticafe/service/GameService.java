package com.example.anticafe.service;

import com.example.anticafe.exception.GameTypeNotFoundException;
import com.example.anticafe.exception.RoomNotFoundException;
import com.example.anticafe.repo.GameRepo;
import com.example.anticafe.repo.GameTypeRepo;
import com.example.anticafe.repo.RoomRepo;
import com.example.anticafe.repo.model.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class GameService {

    private final GameRepo gameRepo;
    private final GameTypeRepo gameTypeRepo;
    private final RoomRepo roomRepo;

    public @NotNull List<Game> fetchAll(){
        return gameRepo.findAll();
    }

    public @NotNull Game fetchById(long id)throws IllegalArgumentException{
        final Optional<Game> maybeGame = gameRepo.findById(id);

        if (maybeGame.isEmpty()) throw new IllegalArgumentException("Game not found");
        else return maybeGame.get();
    }

    public long create(String name, long room_id, long game_type, String number_of_players, String age_rating_IARC, String creator, String genre, String description){
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        GameType gameType = gameTypeRepo.findById(game_type).orElseThrow(GameTypeNotFoundException::new);
        final Game game = new Game(name,room,gameType,number_of_players,age_rating_IARC,creator,genre,description);
        final Game savedGame = gameRepo.save(game);

        return savedGame.getId();
    }

    public void update(Long id, String name, long room_id, long game_type, String number_of_players, String age_rating_IARC, String creator, String genre, String description) throws IllegalArgumentException{
        final Optional<Game> maybeGame = gameRepo.findById(id);
        Room room = roomRepo.findById(room_id).orElseThrow(RoomNotFoundException::new);
        GameType gameType = gameTypeRepo.findById(game_type).orElseThrow(GameTypeNotFoundException::new);
        if (maybeGame.isEmpty()) throw new IllegalArgumentException("Game not found");
        final Game game = maybeGame.get();
        if (name != null && !name.isBlank()) game.setName(name);
        if (room != null) game.setRoom_id(room);
        if (gameType != null) game.setGame_type(gameType);
        if (number_of_players != null && !number_of_players.isBlank()) game.setNumber_of_players(number_of_players);
        if (age_rating_IARC != null && !age_rating_IARC.isBlank()) game.setAge_rating_IARC(age_rating_IARC);
        if (creator != null && !creator.isBlank()) game.setCreator(creator);
        if (genre != null && !genre.isBlank()) game.setGenre(genre);
        if (description != null && !description.isBlank()) game.setDescription(description);

        gameRepo.save(game);
    }

    public void delete(long id)throws IllegalArgumentException{
        gameRepo.deleteById(id);
    }
}
