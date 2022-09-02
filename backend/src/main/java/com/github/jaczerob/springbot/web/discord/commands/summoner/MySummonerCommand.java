package com.github.jaczerob.springbot.web.discord.commands.summoner;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.jaczerob.springbot.web.annotations.Command;
import com.github.jaczerob.springbot.web.annotations.CommandInfo;
import com.github.jaczerob.springbot.web.discord.commands.SlashCommand;
import com.github.jaczerob.springbot.web.entities.SummonerEntity;
import com.github.jaczerob.springbot.web.entities.UserEntity;
import com.github.jaczerob.springbot.web.services.UserService;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Command
public class MySummonerCommand implements SlashCommand {
    @Autowired private UserService users;
    
    @Override
    @CommandInfo(value="summoner", description="Shows your summoner")
    public void reply(SlashCommandInteractionEvent event) {
        Optional<UserEntity> user = users.get(event.getUser());
        if (!user.isPresent()) {
            event.reply("You don't have a summoner registered yet.").queue();
            return;
        }

        SummonerEntity summoner = user.get().getSummoner();
        if (summoner == null) {
            event.reply("You don't have a summoner registered yet.").queue();
            return;
        }

        event.replyEmbeds(summoner.toEmbed()).queue();
    }
}
