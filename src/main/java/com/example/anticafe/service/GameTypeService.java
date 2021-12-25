package com.example.anticafe.service;

import com.example.anticafe.repo.GameTypeRepo;
import com.example.anticafe.repo.model.GameType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class GameTypeService {
    public final GameTypeRepo gameTypeRepo;

    public List<GameType> fetchAll(){
        return gameTypeRepo.findAll();
    }

    public GameType fetchById(long id)throws IllegalArgumentException{
        final Optional<GameType> maybeGameType = gameTypeRepo.findById(id);

        if (maybeGameType.isEmpty()) throw new IllegalArgumentException("Game type not found");
        else return maybeGameType.get();
    }

    public long create(String game_type){
        final GameType gameType = new GameType(game_type);
        final GameType savedGameType = gameTypeRepo.save(gameType);

        return savedGameType.getId();
    }

    public void update(Long id, String game_type) throws IllegalArgumentException{
        final Optional<GameType> maybeGameType = gameTypeRepo.findById(id);

        if (maybeGameType.isEmpty()) throw new IllegalArgumentException("Game type not found");
        final GameType gameType = maybeGameType.get();
        if (game_type != null && !game_type.isBlank()) gameType.setGame_type(game_type);

        gameTypeRepo.save(gameType);
    }

    public void delete(long id)throws IllegalArgumentException{
        gameTypeRepo.deleteById(id);
    }
}
