package com.github.jaczerob.springbot.web.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Component
@PropertySource("classpath:riot.properties")
@ConfigurationProperties
public class RiotProperties {
    private String key;
}
