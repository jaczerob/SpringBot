package com.github.jaczerob.springbot.web.utils;

import net.dv8tion.jda.api.entities.MessageEmbed;

public interface Embeddable {
    MessageEmbed toEmbed();
}
