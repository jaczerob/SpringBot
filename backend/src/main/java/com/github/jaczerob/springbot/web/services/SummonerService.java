package com.github.jaczerob.springbot.web.services;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jaczerob.springbot.web.entities.SummonerEntity;
import com.github.jaczerob.springbot.web.repositories.SummonerRepository;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

@Service
public class SummonerService {
    @Autowired private SummonerRepository summoners;

    public Optional<SummonerEntity> get(String name) {
        return summoners.findSummonerByName(name);
    }

    public SummonerEntity create(Summoner summoner) {
        if (summoners.existsByName(summoner.getName())) throw new EntityExistsException(String.format("Summoner %s exists", summoner.getName()));

        SummonerEntity entity = new SummonerEntity(summoner);
        return summoners.save(entity);
    }

    public SummonerEntity save(SummonerEntity summoner) {
        return summoners.save(summoner);
    }

    public boolean exists(String summoner) {
        return this.summoners.existsByName(summoner);
    }
}
