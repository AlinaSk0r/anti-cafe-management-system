package com.example.anticafe.repo;

import com.example.anticafe.repo.model.PurchaseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseEventRepo extends JpaRepository<PurchaseEvent, Long> {
}
