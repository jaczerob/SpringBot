package com.github.jaczerob.springbot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.github.jaczerob.springbot.web.components.RiotProperties;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;

@SpringBootApplication
@EnableFeignClients
public class SpringbotApplication implements CommandLineRunner {
	@Autowired private RiotProperties riotProperties;

	public static void main(String[] args) {
		SpringApplication.run(SpringbotApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Orianna.setRiotAPIKey(riotProperties.getKey());
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);
	}
}
