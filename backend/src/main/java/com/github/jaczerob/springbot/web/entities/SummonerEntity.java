package com.github.jaczerob.springbot.web.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.jaczerob.springbot.web.utils.Embeddable;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="summoners")
public class SummonerEntity implements Embeddable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int tableId;
    
    private int level;
    private String name;
    private String puuid;
    private String accountId;

    @Column(name="userId")
    private String id;
    private int profileIconId;

    @OneToOne(mappedBy="summoner")
    @JsonBackReference
    private UserEntity user;

    @OneToMany(mappedBy="summoner")
    @JsonManagedReference
    private List<ChampionMasteryEntity> masteries;

    @Temporal(TemporalType.DATE)
    private Date updated;

    public SummonerEntity(Summoner summoner) {
        this.level = summoner.getLevel();
        this.name = summoner.getName();
        this.puuid = summoner.getPuuid();
        this.accountId = summoner.getAccountId();
        this.id = summoner.getId();
        this.profileIconId = summoner.getProfileIcon().getId();
        this.updated = summoner.getUpdated().toDate();
    }

    public MessageEmbed toEmbed() {
        return new EmbedBuilder()
            .setAuthor(this.getUser().getUsername(), null, this.getUser().getAvatarUrl())
            .setTitle(this.getName())
            .addField(new Field("Information", String.format("Level: %d", this.getLevel()), true))
            .build();
    }
}
