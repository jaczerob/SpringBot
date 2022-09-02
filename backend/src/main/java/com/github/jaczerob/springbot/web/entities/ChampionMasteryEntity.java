package com.github.jaczerob.springbot.web.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.jaczerob.springbot.web.utils.Embeddable;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="masteries")
public class ChampionMasteryEntity implements Embeddable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Temporal(TemporalType.DATE)
    private Date lastPlayed;
    private int level;
    private int points;
    private int pointsSinceLastLevel;
    private int pointsUntilNextLevel;

    @ManyToOne
    @JoinColumn(name="summoner_id", referencedColumnName="id")
    private SummonerEntity summoner;

    @ManyToOne
    @JoinColumn(name="champion_id", referencedColumnName="id")
    private ChampionEntity champion;

    public ChampionMasteryEntity(ChampionMastery mastery, SummonerEntity summoner, ChampionEntity champion) {
        this.lastPlayed = mastery.getLastPlayed().toDate();
        this.level = mastery.getLevel();
        this.points = mastery.getPoints();
        this.pointsSinceLastLevel = mastery.getPointsSinceLastLevel();
        this.pointsUntilNextLevel = mastery.getPointsUntilNextLevel();
        this.summoner = summoner;
        this.champion = champion;
    }

    public MessageEmbed toEmbed() {
        return new EmbedBuilder()
            .setAuthor(this.getChampion().getName(), null, this.getChampion().getImageURL())
            .setTitle(String.format("Mastery %d (%d)", this.getLevel(), this.getPoints()))
            .setDescription(String.format("EXP: %d/d", this.getPointsSinceLastLevel(), this.getPointsUntilNextLevel()))
            .build();
    }
}
