package com.github.jaczerob.springbot.web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

@Service
public class LeagueService {
    private static final Logger logger = LogManager.getLogger(SummonerService.class);
    
    public Summoner findSummonerByName(String name) {
        logger.info("attempting to load summoner {} from API", name);
        return Summoner.named(name).get();
    }

    public ChampionMasteries findChampionMasteriesBySummoner(Summoner summoner) {
        return ChampionMasteries.forSummoner(summoner).get();
    }
}
