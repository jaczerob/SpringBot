package com.github.jaczerob.springbot.web.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {
    void reply(SlashCommandInteractionEvent event);
}
