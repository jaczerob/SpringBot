package com.github.jaczerob.springbot.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jaczerob.springbot.web.entities.ChampionMasteryEntity;
import com.github.jaczerob.springbot.web.repositories.ChampionMasteryRepository;

@Service
public class ChampionMasteryService {
    @Autowired private ChampionMasteryRepository masteries;

    public ChampionMasteryEntity save(ChampionMasteryEntity championMasteryEntity) {
        return masteries.save(championMasteryEntity);
    }
}
