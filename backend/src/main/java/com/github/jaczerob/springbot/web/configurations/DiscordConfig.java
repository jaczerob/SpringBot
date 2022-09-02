package com.github.jaczerob.springbot.web.configurations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.security.auth.login.LoginException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.jaczerob.springbot.web.annotations.Command;
import com.github.jaczerob.springbot.web.annotations.CommandInfo;
import com.github.jaczerob.springbot.web.annotations.CommandOption;
import com.github.jaczerob.springbot.web.discord.commands.SlashCommand;
import com.github.jaczerob.springbot.web.components.DiscordProperties;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

@Configuration
public class DiscordConfig {
    private static final Logger logger = LogManager.getLogger(DiscordConfig.class);

    @Autowired private ApplicationContext applicationContext;
    @Autowired private DiscordProperties props;
    @Autowired private List<GatewayIntent> gatewayIntents;

    @Bean
    public JDA getJDA() {
        JDA jda;

        try {
            jda = JDABuilder.createDefault(props.getToken(), gatewayIntents)
                .setEventManager(new AnnotatedEventManager())
                .setStatus(OnlineStatus.ONLINE)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();

            
            jda.awaitReady();
        } catch (LoginException exc) {
            logger.error("Discord token is incorrect.", exc);
            return null;
        } catch (InterruptedException exc) {
            logger.error("JDA interrupted while starting up.", exc);
            return null;
        }
        
        return jda;
    }

    @Bean
    public List<SlashCommandData> getCommands() {
        List<SlashCommandData> commands = new ArrayList<>();

        for (Entry<String, Object> entry : applicationContext.getBeansWithAnnotation(Command.class).entrySet()) {
            if (!(entry.getValue() instanceof SlashCommand)) {
                logger.warn("Bean {} type mismatch: {} is not a SlashCommand", entry.getKey(), entry.getValue().getClass().getName());
                continue;
            }

            SlashCommandData command = this.registerCommand((SlashCommand) entry.getValue());
            if (command == null) {
                logger.warn("Could not register bean {}: no CommandInfo found", entry.getKey());
                continue;
            }


            logger.info("Queuing command {} for registry", entry.getKey());
            commands.add(command);
        }

        this.getJDA().getGuildById("714229842052251699").updateCommands().addCommands(commands).queue();
        return commands;
    }

    private SlashCommandData registerCommand(SlashCommand command) {
        Method[] methods = command.getClass().getMethods();

        for (Method method : methods) {
            if (!method.isAnnotationPresent(CommandInfo.class)) continue;

            CommandInfo info = method.getAnnotation(CommandInfo.class);
            CommandOption[] options = method.getAnnotationsByType(CommandOption.class);
            SlashCommandData slashCommand = Commands.slash(info.value(), info.description())
                .addOptions(Arrays.stream(options).map(DiscordConfig::convertCommandOption).toArray(OptionData[]::new))
                .setDefaultPermissions(convertPermissions(info.permissions()));

            this.getJDA().addEventListener(new AnnotatedEventManager() {
                @SubscribeEvent
                public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
                    if (event.getName().equals(slashCommand.getName())) command.reply(event);
                }
            });

            return slashCommand;
        }

        return null;
    }

    private static OptionData convertCommandOption(CommandOption option) {
        return new OptionData(option.type(), option.value(), option.description(), option.required(), option.autoComplete());
    }

    private static DefaultMemberPermissions convertPermissions(Permission[] permissions) {
        return permissions.length == 0 ? DefaultMemberPermissions.ENABLED : DefaultMemberPermissions.enabledFor(permissions);
    }
}
