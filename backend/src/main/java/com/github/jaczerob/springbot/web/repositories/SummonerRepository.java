package com.github.jaczerob.springbot.web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.jaczerob.springbot.web.entities.SummonerEntity;

public interface SummonerRepository extends JpaRepository<SummonerEntity, Integer> {
    @Query("SELECT s FROM SummonerEntity s WHERE s.name = ?1")
    Optional<SummonerEntity> findSummonerByName(String name);

    @Query("SELECT new java.lang.Boolean(COUNT(s) > 0) FROM SummonerEntity s WHERE s.name = ?1")
    Boolean existsByName(String name);
}
