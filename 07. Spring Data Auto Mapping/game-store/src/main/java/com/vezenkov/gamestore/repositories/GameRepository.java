package com.vezenkov.gamestore.repositories;

import com.vezenkov.gamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Game g WHERE g.id = :id")
    void deleteGamesById(@Param("id") Long id);

    Game findGameByTitle(String title);


}
