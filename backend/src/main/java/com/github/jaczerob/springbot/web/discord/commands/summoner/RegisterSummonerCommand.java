package com.github.jaczerob.springbot.web.discord.commands.summoner;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.jaczerob.springbot.web.annotations.Command;
import com.github.jaczerob.springbot.web.annotations.CommandInfo;
import com.github.jaczerob.springbot.web.annotations.CommandOption;
import com.github.jaczerob.springbot.web.discord.commands.SlashCommand;
import com.github.jaczerob.springbot.web.entities.ChampionEntity;
import com.github.jaczerob.springbot.web.entities.ChampionMasteryEntity;
import com.github.jaczerob.springbot.web.entities.SummonerEntity;
import com.github.jaczerob.springbot.web.services.ChampionMasteryService;
import com.github.jaczerob.springbot.web.services.ChampionService;
import com.github.jaczerob.springbot.web.services.LeagueService;
import com.github.jaczerob.springbot.web.services.SummonerService;
import com.github.jaczerob.springbot.web.services.UserService;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Command
public class RegisterSummonerCommand implements SlashCommand {
    @Autowired private ChampionMasteryService masteries;
    @Autowired private ChampionService champions;
    @Autowired private LeagueService league;
    @Autowired private SummonerService summoners;
    @Autowired private UserService users;
    
    @Override
    @CommandInfo(value="register", description="Registers a summoner to your Discord user")
    @CommandOption(value="name", description="The name of your summoner")
    public void reply(SlashCommandInteractionEvent event) {
        String name = event.getOption("name").getAsString();
        if (summoners.exists(name)) {
            event.reply("Sorry, this summoner is already registered to a user.").queue();
            return;
        }

        Summoner summoner = league.findSummonerByName(name);
        if (!summoner.exists()) {
            event.reply("Sorry, this summoner does not exist.").queue();
            return;
        }

        SummonerEntity summonerEntity = new SummonerEntity(summoner);
        summoners.save(summonerEntity);
        
        ChampionMasteries championMasteries = league.findChampionMasteriesBySummoner(summoner);
        if (championMasteries.exists()) {
            championMasteries.forEach((mastery) -> {
                ChampionEntity championEntity = new ChampionEntity(mastery.getChampion());
                champions.save(championEntity);
    
                ChampionMasteryEntity championMasteryEntity = new ChampionMasteryEntity(mastery, summonerEntity, championEntity);
                masteries.save(championMasteryEntity);
            });
        }
        
        users.create(event.getUser(), summonerEntity);
        event.reply("Your summoner has been registered.").queue();
    }
}
