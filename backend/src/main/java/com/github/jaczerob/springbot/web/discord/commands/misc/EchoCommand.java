package com.github.jaczerob.springbot.web.discord.commands.misc;

import com.github.jaczerob.springbot.web.annotations.Command;
import com.github.jaczerob.springbot.web.annotations.CommandInfo;
import com.github.jaczerob.springbot.web.annotations.CommandOption;
import com.github.jaczerob.springbot.web.discord.commands.SlashCommand;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@Command
public class EchoCommand implements SlashCommand {
    @Override
    @CommandInfo(value="echo", description="Echoes the input message")
    @CommandOption(value="message", type=OptionType.STRING, description="What you want to echo")
    @CommandOption(value="times", type=OptionType.INTEGER, description="How many times you want to echo", required=false)
    public void reply(SlashCommandInteractionEvent event) {
        String message = event.getOption("message").getAsString();

        if (event.getOption("times") == null) {
            event.reply(message).queue();
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < event.getOption("times").getAsInt(); i++)
                builder.append(message).append("\n");
            
            event.reply(builder.toString().trim()).queue();
        }
    }
}
