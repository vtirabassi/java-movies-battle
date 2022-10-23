package com.tirabassi.javamoviesbattle.domain.repositories;

import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RankRepository extends JpaRepository<Rank, Integer> {
    @Query("SELECT r FROM Rank r WHERE r.user.login = ?1")
    Optional<Rank> findByLogin(String login);
}
