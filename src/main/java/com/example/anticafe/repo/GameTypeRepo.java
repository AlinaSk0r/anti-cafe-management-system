package com.example.anticafe.repo;

import com.example.anticafe.repo.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTypeRepo extends JpaRepository<GameType, Long> {
}
