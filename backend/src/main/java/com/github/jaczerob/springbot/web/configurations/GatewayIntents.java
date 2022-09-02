package com.github.jaczerob.springbot.web.configurations;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.dv8tion.jda.api.requests.GatewayIntent;

@Configuration
public class GatewayIntents {
    /**
     * @return A list of all intents the bot needs to function
     */
    @Bean
    public List<GatewayIntent> getGatewayIntents() {
        return Arrays.asList(
            // Gateway intents gives the bot access to the data specified in the intent
            GatewayIntent.DIRECT_MESSAGES,
            GatewayIntent.DIRECT_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_BANS,
            GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
            GatewayIntent.GUILD_INVITES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.MESSAGE_CONTENT
        );
    }
}
