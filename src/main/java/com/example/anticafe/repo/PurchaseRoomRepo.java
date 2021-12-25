package com.example.anticafe.repo;

import com.example.anticafe.repo.model.PurchaseRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRoomRepo extends JpaRepository<PurchaseRoom, Long> {
}

