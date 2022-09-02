package com.github.jaczerob.springbot.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.jaczerob.springbot.web.entities.ChampionEntity;

public interface ChampionRepository extends JpaRepository<ChampionEntity, Integer> {}
