package com.github.jaczerob.springbot.web.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.User;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class UserEntity {
    @Id
    private long id;
    private String username;
    private String discriminator;
    private String avatarUrl;
    private boolean isBot;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="summoner_id", referencedColumnName="id")
    @JsonManagedReference
    private SummonerEntity summoner;

    public UserEntity(User user) {
        this.id = user.getIdLong();
        this.username = user.getName();
        this.discriminator = user.getDiscriminator();
        this.avatarUrl = user.getEffectiveAvatarUrl();
        this.isBot = user.isBot();
    }

    public UserEntity(User user, SummonerEntity summoner) {
        this.id = user.getIdLong();
        this.username = user.getName();
        this.discriminator = user.getDiscriminator();
        this.avatarUrl = user.getEffectiveAvatarUrl();
        this.isBot = user.isBot();
        this.summoner = summoner;
    }
}
