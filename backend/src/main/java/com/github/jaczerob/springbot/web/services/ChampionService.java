package com.github.jaczerob.springbot.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jaczerob.springbot.web.entities.ChampionEntity;
import com.github.jaczerob.springbot.web.repositories.ChampionRepository;

@Service
public class ChampionService {
    @Autowired private ChampionRepository champions;

    public ChampionEntity save(ChampionEntity championEntity) {
        return this.champions.save(championEntity);
    }
}
